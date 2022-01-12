package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Actor extends UntypedAbstractActor{
	
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef refTransmitter;
	private ActorRef refA;
	private ActorRef refB;

	public Actor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Actor.class, () -> {
			return new Actor();
		});
	}


	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ArrayList<?> && ((ArrayList<?>)message).size() == 2){
			// ArrayList message from system means this actor is a
			log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
			
			// Update the reference to the transmitter
			this.refTransmitter = ((ArrayList<ActorRef>)message).get(0);
			log.info("Transmitter reference updated ! New reference is: {}", this.refTransmitter);
			
			// Update the reference to b
			this.refB = ((ArrayList<ActorRef>)message).get(1);
			log.info("B reference updated ! New reference is: {}", this.refB);
			
		} else if (message instanceof String) {
			// String message from system means this actor is a
			log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
			
			if (refTransmitter != null && (String)message == "start") {
				// Send "hello" to the transmitter
				refTransmitter.tell("hello", getSelf());
				log.info("["+getSelf().path().name()+"] sent message to ["+ refTransmitter +"]: hello");
				
				// Send the reference to b to the transmitter
				refTransmitter.tell(refB, getSelf());
				log.info("["+getSelf().path().name()+"] sent message to ["+ refTransmitter +"]: " + refB);
				
			} else if ((String)message == "hello") {
				this.refA = getSender();
				refA.tell("hi!", getSelf());
				log.info("["+getSelf().path().name()+"] sent message to ["+ refTransmitter +"]: hi!");
				
			} else if ((String)message == "hi!") {
				log.info("["+getSelf().path().name()+"] received message to ["+ refTransmitter +"]: hi!");
			}
			
		}
	}
}
