package demo;

import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ShortestLengthFlooding {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");

		final ActorRef a = system.actorOf(DefaultActor.createActor(), "a");
		final ActorRef b = system.actorOf(DefaultActor.createActor(), "b");
		final ActorRef c = system.actorOf(DefaultActor.createActor(), "c");
		final ActorRef d = system.actorOf(DefaultActor.createActor(), "d");
		final ActorRef e = system.actorOf(DefaultActor.createActor(), "e");
		final ActorRef f = system.actorOf(DefaultActor.createActor(), "f");
		final ActorRef g = system.actorOf(DefaultActor.createActor(), "g");
		final ActorRef h = system.actorOf(DefaultActor.createActor(), "h");
		final ActorRef i = system.actorOf(DefaultActor.createActor(), "i");
		final ActorRef j = system.actorOf(DefaultActor.createActor(), "j");
		final ActorRef k = system.actorOf(DefaultActor.createActor(), "k");
		final ActorRef l = system.actorOf(DefaultActor.createActor(), "l");
		final ActorRef m = system.actorOf(DefaultActor.createActor(), "m");
		final ActorRef n = system.actorOf(DefaultActor.createActor(), "n");
		final ActorRef o = system.actorOf(DefaultActor.createActor(), "o");
		final ActorRef p = system.actorOf(DefaultActor.createActor(), "p");
		final ActorRef q = system.actorOf(DefaultActor.createActor(), "q");
		final ActorRef r = system.actorOf(DefaultActor.createActor(), "r");

		// Keep ActorRef in ArrayList for easier handling
		ArrayList<ActorRef> actors = new ArrayList<ActorRef>(
				Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r));

		// Adjacency matrix definition
		int[][] adjMatrix = { { 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 } };

		// Adjacency matrix dimensions
		int rows = adjMatrix.length;
		int cols = adjMatrix[0].length;

		// Send reference of other actors to a, b, c, ...
		for (int idx = 0; idx < rows; idx++) {
			for (int jdx = 0; jdx < cols; jdx++) {
				if (adjMatrix[idx][jdx] == 1) {
					actors.get(idx).tell(actors.get(jdx), ActorRef.noSender());
				}
			}
		}

		// Start the flooding with a
		i.tell(new Message(0, "hi", 0), ActorRef.noSender());
		// a.tell(new Message(1, "hello"), ActorRef.noSender());
		// a.tell(new Message(2, "hello"), ActorRef.noSender());

		// We wait 10 seconds before ending system (by default)
		// But this is not the best solution.
		try {
			waitBeforeTerminate();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(10000);
	}

}
