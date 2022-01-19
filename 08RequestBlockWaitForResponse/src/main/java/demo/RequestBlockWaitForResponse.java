package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RequestBlockWaitForResponse {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
	    final ActorRef a = system.actorOf(Requester.createActor(), "a");
	    final ActorRef b = system.actorOf(Responder.createActor(), "b");
		
	    // Send ref of b to a
		a.tell(b, ActorRef.noSender());

	    // We wait 10 seconds before ending system (by default)
	    // But this is not the best solution.
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}
	
	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(10000);
	}
}
