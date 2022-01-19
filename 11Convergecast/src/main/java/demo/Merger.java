package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.UntypedAbstractActor;
import java.util.ArrayList;

public class Merger extends UntypedAbstractActor {
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Merger() {};
	
	private ActorRef LastActor;
	private ArrayList<ActorRef> joiningActors = new ArrayList<ActorRef>();
	private ArrayList<String> receivedMessages = new ArrayList<String>();
	private String currentMessage;
	boolean messageFlag = true;

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Merger.class, () -> {
			return new Merger();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		// Update reference to the last actor
		if (message instanceof ActorRef) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: LastActor reference"); 
			LastActor = (ActorRef)message;
		}
		
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + (String) message);
			
			if ((String)message == "join") {
				joiningActors.add(getSender());
				// Initialize ArrayList of messages
				receivedMessages.add("default");
			} else if ((String)message == "unjoin") {
				receivedMessages.remove(joiningActors.indexOf(getSender()));
				joiningActors.remove(getSender());
				
				//// Reduce the size of the ArrayList after removal
				// joiningActors.trimToSize();
			} else if (((String)message).startsWith("hi")) {
				currentMessage = (String)message;
				// Update received message correspond
				receivedMessages.set(joiningActors.indexOf(getSender()), (String)message);
				
				// Check if all received messages are the same as the new one 
				messageFlag = true;
				for (String elmt : receivedMessages) {
					if (elmt != null && !elmt.equals(currentMessage)) {
						messageFlag = false;
					}
				}
				
				// Send message to last actor if previous condition is true
				if (messageFlag) {
					LastActor.tell(currentMessage, getSelf());
				}
				

			}
		}
		
		}	
}
