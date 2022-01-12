package demo;

import java.util.ArrayList;

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
		return Props.create(Actor.class, () -> {
			return new Actor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			this.refB = (ActorRef) message;
		} else if (message instanceof ResponseMessage) {
			log.info("["+getSelf().path().name()+"] received response from ["+ getSender().path().name() +"]: ID " 
					+ ((ResponseMessage)message).getId() + " message " + ((ResponseMessage)message).getMessage());
		}
	}
}