package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Listener extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Actor reference
	public Listener() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Listener.class, () -> {
			return new Listener();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ResponseMessage) {
			// Print request message from a
			log.info("[" + getSelf().path().name() + "] received response from [" + getSender().path().name() + "]: ID "
					+ ((ResponseMessage) message).getId() + ", message " + ((ResponseMessage) message).getMessage());

		}
	}
}
