package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class BroadcastRoundRobin {
	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef a = system.actorOf(FirstActor.createActor(), "a");
		final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");
		final ActorRef b = system.actorOf(JoiningActor.createActor(), "b");
		final ActorRef c = system.actorOf(JoiningActor.createActor(), "c");

		// Send ref of broadcaster to all actors
		a.tell(broadcaster, ActorRef.noSender());
		b.tell(broadcaster, ActorRef.noSender());
		c.tell(broadcaster, ActorRef.noSender());

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
