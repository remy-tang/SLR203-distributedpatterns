package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;

public class DefaultActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	// Actor references
	private ArrayList<ActorRef> actorRefs = new ArrayList<ActorRef>();
	
	// Received sequence numbers
	private ArrayList<Integer> receivedNum = new ArrayList<Integer>();

	public DefaultActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(DefaultActor.class, () -> {
			return new DefaultActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			ActorRef m = (ActorRef)message;
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + m.toString());
			actorRefs.add(m);
		} else if (message instanceof Message) {
			Message m = (Message) message;
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + m.toString());
			
			// Forward message if the sequence number is new
			int seqNum = m.getSequenceNumber();
			if (!receivedNum.contains(seqNum)) {
				receivedNum.add(seqNum);
				for (ActorRef actor : actorRefs) {
					actor.tell(m, getSelf());
				}
			}
		}
	}
}
