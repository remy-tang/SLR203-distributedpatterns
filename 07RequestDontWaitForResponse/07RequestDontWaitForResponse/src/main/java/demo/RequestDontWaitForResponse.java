package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RequestDontWaitForResponse {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
	    final ActorRef a = system.actorOf(Actor.createActor(), "a");
	    final ActorRef b = system.actorOf(Actor.createActor(), "b");
		
	    // Send ref of b to a
		a.tell(b, ActorRef.noSender());

	    // We wait 5 seconds before ending system (by default)
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
		Thread.sleep(5000);
	}
}
