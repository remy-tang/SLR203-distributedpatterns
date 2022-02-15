package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class SearchActors {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");

		final ActorRef a = system.actorOf(ActorCreator.createActor(), "a");

		// Send CREATE message to a, n times
		int n = 2;
		for (int i = 0; i < n; i++)
			a.tell(new CreateMessage(), ActorRef.noSender());

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
