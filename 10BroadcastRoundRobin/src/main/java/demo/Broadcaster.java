package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Broadcaster extends UntypedAbstractActor {
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Broadcaster() {
	}

	private ArrayList<ActorRef> joiningActors = new ArrayList<>();

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Broadcaster.class, () -> {
			return new Broadcaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]:"
					+ (String) message);
			if (((String) message).equals("join")) {
				joiningActors.add(getSender());
			} else if (((String) message).equals("m")) {
				// Send messages to the actors that have sent the join message to this actor
				for (ActorRef joiner : joiningActors) {
					joiner.tell("m", getSelf());
				}
			}
		}

	}
}
