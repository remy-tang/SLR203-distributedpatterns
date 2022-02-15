package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class PublishSubscribe {

	public static void main(String[] args) {
		final ActorSystem system = ActorSystem.create("system");

		final ActorRef a = system.actorOf(Subscriber.createActor(), "a");
		final ActorRef b = system.actorOf(Subscriber.createActor(), "b");
		final ActorRef c = system.actorOf(SubUnsub.createActor(), "c");

		final ActorRef publisher1 = system.actorOf(HelloPublisher.createActor(), "publisher1");
		final ActorRef publisher2 = system.actorOf(WorldPublisher.createActor(), "publisher2");

		final ActorRef topic1 = system.actorOf(Topic.createActor(), "topic1");
		final ActorRef topic2 = system.actorOf(Topic.createActor(), "topic2");

		// Send ref of topics to subscribe to, to a, b, c
		a.tell(topic1, ActorRef.noSender());
		b.tell(topic1, ActorRef.noSender());
		b.tell(topic2, ActorRef.noSender());
		c.tell(topic2, ActorRef.noSender());

		// Send ref of topics to publishers
		publisher1.tell(topic1, ActorRef.noSender());
		publisher2.tell(topic2, ActorRef.noSender());

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