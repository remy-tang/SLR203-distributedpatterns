package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;

public class Topic extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	// Subscribed actors
	private ArrayList<ActorRef> subbedActors = new ArrayList<ActorRef>();
	
	// Timeout
	public Topic() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Topic.class, () -> {
			return new Topic();
		});
	}
	
	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof Unsubscribe) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: Unsubscribe request");
			subbedActors.remove(getSender());
		}
		
		if (message instanceof Subscribe) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: Subscribe request");
			subbedActors.add(getSender());
		}
		
		if (message instanceof String) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + (String) message);
			
			// Tell subscribers about the message
			for (ActorRef subbed : subbedActors) {
				subbed.tell((String)message, getSelf());
			}
		}
	}
}
