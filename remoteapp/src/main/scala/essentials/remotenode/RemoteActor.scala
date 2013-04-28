package essentials.remotenode

import akka.actor.Actor

class RemoteActor extends Actor {
  def receive: Receive = {
    case message: String =>
      // Get reference to the message sender and reply back
      sender.tell(message + " message received")
  }
}
