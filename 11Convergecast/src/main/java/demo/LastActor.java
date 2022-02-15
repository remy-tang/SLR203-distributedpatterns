package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LastActor extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Actor reference

	// Timeout
	public LastActor() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(LastActor.class, () -> {
			return new LastActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String) {
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]:"
					+ (String) message);
		}
	}
}
