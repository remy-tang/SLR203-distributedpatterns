package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class JoinActor extends UntypedAbstractActor{
	
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	// Actor reference
	private ActorRef refMerger;
	
	// Private counter
	private int counter = 1;

	public JoinActor() {};

	// Static function creating actor
	public static Props createActor() {
		return Props.create(JoinActor.class, () -> {
			return new JoinActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: Merger Reference ");
			refMerger = (ActorRef)message;
			
			//Send joining message to the merger
			refMerger.tell("join", getSelf());
			
			// Tell scheduler to send "go" in 1 second
			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
		}
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + (String) message);
			if ((String)message == "go") {
				
				// Send "hi" message to merger
				if (counter == 1)
					refMerger.tell("hi", getSelf());
				else {
					refMerger.tell("hi" + counter, getSelf());
				}
				
				counter++;
				// Tell scheduler to send "go" in 1 second
				getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
			}
		}
	}
}
