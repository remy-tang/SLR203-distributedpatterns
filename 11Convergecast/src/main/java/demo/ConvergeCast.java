package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ConvergeCast {
	
	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");
	    final ActorRef a = system.actorOf(JoinActor.createActor(), "a");
	    final ActorRef b = system.actorOf(JoinActor.createActor(), "b");
	    final ActorRef c = system.actorOf(JoinUnjoinActor.createActor(), "c");
	    final ActorRef merger = system.actorOf(Merger.createActor(), "merger");
	    final ActorRef d = system.actorOf(LastActor.createActor(), "d");
		
	    // Send ref of merger to a, b, c
		a.tell(merger, ActorRef.noSender());
		b.tell(merger, ActorRef.noSender());
		c.tell(merger, ActorRef.noSender());
		// Send ref of d to merger
		merger.tell(d, ActorRef.noSender());

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