package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Requester extends UntypedAbstractActor{
	
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	// Actor reference
	private ActorRef refB;

	public Requester() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Requester.class, () -> {
			return new Requester();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			this.refB = (ActorRef) message;
			
			// Send 30 messages to B with incremental IDs
			RequestMessage newMessage;
			for (int i = 0; i <30; i++) {
				newMessage = new RequestMessage(i, "This is a request");
				refB.tell(newMessage, getSelf());
				log.info("["+getSelf().path().name()+"] sent request to ["+ getSender().path().name() +"]: ID " 
						+ ((RequestMessage)newMessage).getId() + ", message " + ((RequestMessage)newMessage).getMessage());
			}
			
		} else if (message instanceof ResponseMessage) {
			// Print response message from B
			log.info("["+getSelf().path().name()+"] received response from ["+ getSender().path().name() +"]: ID " 
					+ ((ResponseMessage)message).getId() + ", message " + ((ResponseMessage)message).getMessage());
		}
	}
}