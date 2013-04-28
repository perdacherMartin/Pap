package at.univie.pap.matrixvec.actors

import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter
import at.univie.pap.matrixvec.Calculate
import at.univie.pap.matrixvec.ResultCell
import at.univie.pap.matrixvec.Work
import akka.actor.ActorRef
import at.univie.pap.matrixvec.ResultVector
import akka.util.duration._

class Master(nrOfWorkers:Int, n:Int, listener: ActorRef) extends Actor {

  var countOfResults: Int = 0
  val start: Long = System.currentTimeMillis
  var resultvec:Array[Double] = new Array[Double](n)
  var customer : ActorRef = null
  
  val workerRouter = context.actorOf(
		  Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")
 
  def receive = {
	// handle messages ...
    case Calculate(matrix, vector) =>
      customer = sender
      for (i <- 0 until matrix.size ) {
        workerRouter ! Work(matrix(i), vector , i);
      }
        
      
    case ResultCell(value,offset) =>
      resultvec(offset) = value
      countOfResults += 1
      
      if (countOfResults >= n  ) {
    	  // Send the result to the listener
    	  customer ! ResultVector(resultvec, duration = (System.currentTimeMillis - start).millis, nrOfWorkers)
    	  // Stops this actor and all its supervised children
    	  context.stop(self)
      }
  }	
}