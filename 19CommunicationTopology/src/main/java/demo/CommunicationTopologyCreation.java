package demo;

import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;;

/**
 * Hello world!
 *
 */
public class CommunicationTopologyCreation {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");

		final ActorRef a = system.actorOf(DefaultActor.createActor(), "a");
		final ActorRef b = system.actorOf(DefaultActor.createActor(), "b");
		final ActorRef c = system.actorOf(DefaultActor.createActor(), "c");
		final ActorRef d = system.actorOf(DefaultActor.createActor(), "d");

		// Keep ActorRef in ArrayList for easier handling
		ArrayList<ActorRef> actors = new ArrayList<>(Arrays.asList(a, b, c, d));

		// Adjacency matrix definition
		int[][] adjMatrix = { { 0, 1, 1, 0 }, { 0, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 0, 0, 1 } };

		// Adjacency matrix dimensions
		int n = 4;
		int p = 4;

		// Send reference of other actors to a, b, c, d
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < p; j++) {
				if (adjMatrix[i][j] == 1) {
					actors.get(i).tell(actors.get(j), ActorRef.noSender());
				}
			}
		}

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
