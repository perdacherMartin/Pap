package at.univie.pap.matrixvec.actors

import akka.actor.Actor
import at.univie.pap.matrixvec.Work
import at.univie.pap.matrixvec.ResultCell

class Worker extends Actor {
	def receive = {
	  case Work(matrixLine, vector, offset) =>
	    sender ! calculateCell(matrixLine, vector, offset)
	}
	
	def calculateCell(matrixLine:Array[Double], vector:Array[Double], offset:Int) : ResultCell = {
		var result : Double = 0.0;
	  	val mult : Array[Double] = matrixLine 
	  	
		for ( i <- 0 until matrixLine.length ){
		  result += ( matrixLine(i) * vector(i) );
		}
	  
		new ResultCell(value = result,offset = offset);
	}
}