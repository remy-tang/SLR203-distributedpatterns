package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class DefaultActor extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Actor references
	private ArrayList<ActorRef> actorRefs = new ArrayList<>();

	// Received sequence numbers
	private ArrayList<Integer> receivedNum = new ArrayList<>();

	// Corresponding lengths
	private ArrayList<Integer> lengths = new ArrayList<>();

	public DefaultActor() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(DefaultActor.class, () -> {
			return new DefaultActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			ActorRef m = (ActorRef) message;
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]:"
					+ m.toString());
			actorRefs.add(m);
		} else if (message instanceof Message) {
			Message m = (Message) message;

			// Print message information (sender, receiver, content...)
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]:"
					+ m.toString());

			// Forward message if the sequence number is new OR message length is shorter
			int seqNum = m.getSequenceNumber();
			int len = m.getLength();
			if (!receivedNum.contains(seqNum)) {
				receivedNum.add(seqNum);
				lengths.add(len);

				for (ActorRef actor : actorRefs) {
					actor.tell(new Message(m.getSequenceNumber(), m.getContent(), m.getLength() + 1), getSelf());
				}
			} else if (Integer.compare(lengths.get(receivedNum.indexOf(seqNum)), len) > 0) {
				// Update private length if newly received length is shorter
				lengths.set(receivedNum.indexOf(seqNum), len);

				for (ActorRef actor : actorRefs) {
					actor.tell(new Message(m.getSequenceNumber(), m.getContent(), m.getLength() + 1), getSelf());
				}
			}
		}
	}
}
