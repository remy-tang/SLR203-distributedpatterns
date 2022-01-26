package demo;

import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class ControlledFlooding {
	
    public static void main(String[] args) {
    	
	    final ActorSystem system = ActorSystem.create("system");
		
	    final ActorRef a = system.actorOf(DefaultActor.createActor(), "a");
	    final ActorRef b = system.actorOf(DefaultActor.createActor(), "b");
	    final ActorRef c = system.actorOf(DefaultActor.createActor(), "c");
	    final ActorRef d = system.actorOf(DefaultActor.createActor(), "d");
	    final ActorRef e = system.actorOf(DefaultActor.createActor(), "e");
	    
	    // Keep ActorRef in ArrayList for easier handling
	    ArrayList<ActorRef> actors = new ArrayList<ActorRef>(Arrays.asList(a,b,c,d,e));
	    
	    // Adjacency matrix definition
		int[][] adjMatrix = {{0,1,1,0,0}, {0,0,0,1,0}, {0,0,0,1,0}, 
							 {0,0,0,0,1}, {0,1,0,0,0}};
		
		// Adjacency matrix dimensions
		int n = adjMatrix.length;
		int p = adjMatrix[0].length;
	    
	    // Send reference of other actors to a, b, c, d
		for (int i=0; i<n; i++) {
			for (int j=0; j<p; j++) {
				if (adjMatrix[i][j] == 1) {
					actors.get(i).tell(actors.get(j), ActorRef.noSender());
				}
			}
		}
		
		// Start the flooding with a
		a.tell(new Message(0, "hello"), ActorRef.noSender());
		//a.tell(new Message(1, "hello"), ActorRef.noSender());
		//a.tell(new Message(2, "hello"), ActorRef.noSender());
	
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
