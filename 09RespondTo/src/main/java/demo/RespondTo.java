package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class RespondTo {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
	    final ActorRef a = system.actorOf(Requester.createActor(), "a");
	    final ActorRef b = system.actorOf(TransmitterActor.createActor(), "b");
	    final ActorRef c = system.actorOf(Listener.createActor(), "c");

	    // Create list of references
	    ArrayList<ActorRef> actors = new ArrayList<>();
	    actors.add(b);
	    actors.add(c);

	    // Send ref of b to a
	    a.tell(actors, ActorRef.noSender());

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
