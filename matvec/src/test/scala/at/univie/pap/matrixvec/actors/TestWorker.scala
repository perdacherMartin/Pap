package at.univie.pap.matrixvec.actors

import org.junit.Assert
import com.typesafe.config.ConfigFactory
import akka.actor.ActorSystem
import akka.dispatch.Await
import akka.pattern.ask
import akka.testkit.TestActorRef
import akka.util.duration.intToDurationInt
import akka.util.Timeout
import junit.framework.TestCase
import org.junit.runner.RunWith
import at.univie.pap.matrixvec.Work
import at.univie.pap.matrixvec.ResultCell

class TestWorker extends TestCase {
	
	implicit val system = ActorSystem("TestSys", ConfigFactory
			.load().getConfig("TestSys"))
			

	def testWorker() {
		val actorRef = TestActorRef[Worker]
		val actor: Worker = actorRef.underlyingActor
		
		val vector1 : Array[Double] = new Array[Double](100);
		val value1 : Double = 2.0
		val vector2 : Array[Double] = new Array[Double](100);
		val value2 : Double = 2.0
		
		for ( i <- 0 until 100 ){
		  vector1(i) = value1
		  vector2(i) = value2
		}
		
		val expectedResult = value1 * value2 * 100
		
		implicit val timeout = Timeout(5 seconds)
		val future = (actorRef ? Work(vector1, vector2 , 0)).mapTo[ResultCell] 
		val result : ResultCell = Await.result(future, timeout.duration)
		
		Assert.assertEquals(result.value, expectedResult, 0.1)
	}
}