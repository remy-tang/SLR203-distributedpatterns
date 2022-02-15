package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Responder extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Actor reference
	private ActorRef refA;

	public Responder() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Responder.class, () -> {
			return new Responder();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		// Update reference to a if empty
		refA = getSender();

		if (message instanceof RequestMessage) {
			// Print request message from a
			log.info("[" + getSelf().path().name() + "] received request from [" + getSender().path().name() + "]: ID "
					+ ((RequestMessage) message).getId() + ", message " + ((RequestMessage) message).getMessage());

			// Send response to a
			ResponseMessage newMessage = new ResponseMessage(((RequestMessage) message).getId(), "This is a response.");
			refA.tell(newMessage, getSelf());
		}
	}
}
