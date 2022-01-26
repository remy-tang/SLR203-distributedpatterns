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
	private ActorRef refC;

	public Requester() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Requester.class, () -> {
			return new Requester();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ArrayList<?> && ((ArrayList<?>)message).size() == 2){
			// ArrayList message from system means this actor is a
			log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");

			try {
				// Update the reference to the transmitter
				this.refB = ((ArrayList<ActorRef>)message).get(0);
				log.info("B reference updated ! New reference is: {}", this.refB);
	
				// Update the reference to b
				this.refC = ((ArrayList<ActorRef>)message).get(1);
				log.info("C reference updated ! New reference is: {}", this.refC);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Create and send message to b
			RequestMessage request1 = new RequestMessage(0, "Request from A.");
			RequestAndSenderMessage ras = new RequestAndSenderMessage(0, request1, refC);
			refB.tell(ras, getSelf());

		}
	}
}