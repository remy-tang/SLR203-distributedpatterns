package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.util.Timeout;

public class FirstActor extends UntypedAbstractActor{
	
	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	// Actor reference
	private ActorRef refBroadcaster;
	
	// Timeout
	Timeout timeout = Timeout.create(Duration.ofSeconds(5));

	public FirstActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef) {
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: Broadcaster Reference ");
			refBroadcaster = (ActorRef)message;
			// Tell scheduler to send "go" in 1 second
			getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system().dispatcher(), ActorRef.noSender());
		}
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + (String) message);
			if (((String)message).equals("go")) {
				refBroadcaster.tell("m", getSelf());
			}
		}
		
	}
}
