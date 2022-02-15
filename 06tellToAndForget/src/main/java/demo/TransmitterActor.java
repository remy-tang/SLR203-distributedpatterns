package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TransmitterActor extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef refA;
	private ActorRef refB;
	private String fwdMessage;

	public TransmitterActor() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(TransmitterActor.class, () -> {
			return new TransmitterActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof String && (String) message == "hello") {

			// We receive String from a :
			// update reference to a and save the string content of the message
			this.refA = getSender();
			log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name()
					+ "]: hello");
			fwdMessage = (String) message;

		} else if (message instanceof ActorRef && this.refA != null) {
			// Update the reference to B
			log.info("[" + getSelf().path().name() + "] received message from [" + getSender().path().name() + "]");
			this.refB = (ActorRef) message;
			refB.tell(fwdMessage, refA);
		}
	}

}
