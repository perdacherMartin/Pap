package at.univie.pap.matrixvec.actors

import akka.actor.Actor
import at.univie.pap.matrixvec.ResultVector

class ResultListener extends Actor {
	def receive = {
	  case ResultVector(vector,duration,nrOfWorkers) =>
		  
	    /*
		  vector.foreach{ elem:Double =>
		    print(elem + ", ")
		  }
		*/    
		  println("\n\tCalculation time: %s"
				.format(duration))
		  println("\tmatrix size: " + vector.size )
		  println("\tWorkerNumber: " + nrOfWorkers + "\n")
		  //println( vector.size + ";" + nrOfWorkers + ";%s".format(duration))
		  context.system.shutdown()
	}
}