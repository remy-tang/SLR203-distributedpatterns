package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class TransmitterActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public TransmitterActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(TransmitterActor.class, () -> {
			return new TransmitterActor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		if(message instanceof RequestAndSenderMessage){
			RequestAndSenderMessage receivedMessage = (RequestAndSenderMessage) message;
			ResponseMessage msg = new ResponseMessage(receivedMessage.getId(), "This is the response from B to C");
			receivedMessage.getRef().tell(msg, getSelf());
			log.info("["+getSelf().path().name()+"] sent response to [" + receivedMessage.getRef() + "]");
		}
	}
}
