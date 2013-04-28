package at.univie.pap.matrixvec

import akka.pattern.ask
import scala.Array.ofDim
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.util.Duration
import at.univie.pap.matrixvec.actors.Master
import at.univie.pap.matrixvec.actors.ResultListener
import akka.util.Timeout
import akka.util.duration._
import akka.dispatch.Future
import akka.dispatch.Await
import akka.actor.ActorRef
import Numeric._
import grizzled.math.stats.popStdDev
import grizzled.math.stats.arithmeticMean

sealed trait MatVecMessage
case class Calculate(matrix: Array[Array[Double]], vector: Array[Double]) extends MatVecMessage
case class Work(matrixLine: Array[Double], vector: Array[Double], offset:Int) extends MatVecMessage
case class ResultCell(value: Double, offset:Int) extends MatVecMessage
case class ResultVector(vector: Array[Double], duration: Duration, nrOfWorkers:Int) extends MatVecMessage


object MatrixVec extends App {
	
	for ( i <- 1 to 20 ){
		calculate(nrOfWorkers=i,parFactor=3, matrixSize=10000)  
	}
    
	def calculate(nrOfWorkers:Int, parFactor:Int, matrixSize:Int){
    	val nProcessors = Runtime.getRuntime.availableProcessors
		def generateMatrix() : Array[Array[Double]] = {
		  val myMatrix : Array[Array[Double]] = ofDim[Double](matrixSize,matrixSize)
		  
		  for ( i <- 0 until myMatrix.length ){
		    for ( j <- 0 until myMatrix.length ){
		      myMatrix(i)(j) = j
		    }
		  }
		  
		  myMatrix
		}
		
		def generateVector() : Array[Double] = {
		  val myVector : Array[Double] = new Array[Double](matrixSize)
		  
		  for ( i <- 0 until myVector.length ){
		    myVector(i) = i
		  }
		  
		  myVector
		}
		
		val configString1: String = """akka {
		  logConfigOnStart=off
		  executor = "thread-pool-executor"
		  fork-join-executor {
				parallelism-min = %d
				parallelism-factor = %d.0
				parallelism-max = %d
		  }
		}""".format(nProcessors, parFactor, nProcessors)
		
		// initialize the actor system with configuration above
		val system = ActorSystem("MatVecSystem", ConfigFactory.parseString(configString1))
 
		// create the result listener, which will print the result and shutdown the system
		val listener = system.actorOf(akka.actor.Props[ResultListener], name = "listener")
 
		// create the master
		//var master:ActorRef = null
		
		implicit val timeout = Timeout(5 seconds);
		var runtime = Array[Long]();
		
		for ( i <- 0 until 4 ){ // warmup phase, because of just in time compilation in the JVM
			val master = system.actorOf(Props(new Master(nrOfWorkers, matrixSize,  listener)), name = "masterjit" + i)
			val temp:Future[ResultVector] = ( master ? Calculate(generateMatrix(), generateVector()) ).mapTo[ResultVector]
			val result:ResultVector = Await.result(temp, timeout.duration).asInstanceOf[ResultVector]
			// println("Warmup:" + result.duration.toMillis)
		}
		
		
		for ( i <- 0 until 10 ){ // time measurement
			val master = system.actorOf(Props(new Master(nrOfWorkers, matrixSize,  listener)), name = "master" + i)
			val temp:Future[ResultVector] = ( master ? Calculate(generateMatrix(), generateVector()) ).mapTo[ResultVector]
			val result:ResultVector = Await.result(temp, timeout.duration).asInstanceOf[ResultVector]
			runtime = runtime :+ result.duration.toMillis
		}
		
		val stdDev:Long = popStdDev(runtime: _*).toLong
		val mean:Long = arithmeticMean(runtime: _*).toLong
		println(nProcessors + ";" + matrixSize + ";" + nrOfWorkers + ";" + parFactor + ";" + (mean - stdDev) + ";" +  2*stdDev)

		
		system.shutdown();
	}
}