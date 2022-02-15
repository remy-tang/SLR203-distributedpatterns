package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleActor extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef actorRef;

	public SimpleActor() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(SimpleActor.class, () -> {
			return new SimpleActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {

	}
}
