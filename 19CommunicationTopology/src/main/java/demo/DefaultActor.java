package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class DefaultActor extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor references
	private ArrayList<ActorRef> actorRefs = new ArrayList<>();

	public DefaultActor() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(DefaultActor.class, () -> {
			return new DefaultActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			ActorRef m = (ActorRef) message;
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]:"
					+ m.toString());
			actorRefs.add(m);
		}
	}
}
