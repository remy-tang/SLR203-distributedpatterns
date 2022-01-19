package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Subscriber extends UntypedAbstractActor{
	
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	public Subscriber() {};

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Subscriber.class, () -> {
			return new Subscriber();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: Topic Reference ");
			
			//Subscribe to topic
			((ActorRef)message).tell(new Subscribe(), getSelf());
		}
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + (String) message);
		}
	}
}