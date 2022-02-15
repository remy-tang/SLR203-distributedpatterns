package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class TellToAndForget {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef a = system.actorOf(MediatorActor.createActor(), "a");
		final ActorRef b = system.actorOf(MediatorActor.createActor(), "b");
		final ActorRef transmitter = system.actorOf(TransmitterActor.createActor(), "transmitter");

		// Create list of references
		ArrayList<ActorRef> actors = new ArrayList<>();
		actors.add(transmitter);
		actors.add(b);

		// Send messages
		a.tell(actors, ActorRef.noSender());
		a.tell("start", ActorRef.noSender());

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
