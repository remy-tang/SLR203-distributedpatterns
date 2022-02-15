package demo;

import java.time.Duration;
import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class WorldPublisher extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Topics to publish to
	private ArrayList<ActorRef> topics = new ArrayList<>();

	public WorldPublisher() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(WorldPublisher.class, () -> {
			return new WorldPublisher();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name()
					+ "]: Topic Reference ");

			// Add topic to publishing list
			topics.add((ActorRef) message);

			// Wait 1 second before publishing (wait for subscribers)
			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1500), getSelf(), "go",
					getContext().system().dispatcher(), ActorRef.noSender());
		}
		if (message instanceof String) {
			if (((String) message).equals("go")) {

				for (ActorRef topic : topics) {
					topic.tell("world", getSelf());
				}
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1500), getSelf(), "go",
						getContext().system().dispatcher(), ActorRef.noSender());
			}
		}
	}
}
