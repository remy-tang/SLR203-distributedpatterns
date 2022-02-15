package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SubUnsub extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private ArrayList<ActorRef> subbedTopics = new ArrayList<>();

	public SubUnsub() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(SubUnsub.class, () -> {
			return new SubUnsub();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name()
					+ "]: Topic Reference ");

			// Subscribe to topic
			((ActorRef) message).tell(new Subscribe(), getSelf());
			subbedTopics.add((ActorRef) message);
		}
		if (message instanceof String) {
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]:"
					+ (String) message);
			if (((String) message).equals("world")) {
				// Unsubscribe from all topics
				for (ActorRef topic : subbedTopics) {
					topic.tell(new Unsubscribe(), getSelf());
				}
			}
		}
	}
}
