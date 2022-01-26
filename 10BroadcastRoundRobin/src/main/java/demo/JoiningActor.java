package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class JoiningActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	
	// Actor reference
	private ActorRef refBroadcaster;
	
	// Timeout
	public JoiningActor() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(JoiningActor.class, () -> {
			return new JoiningActor();
		});
	}
	
	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]: Broadcaster Reference ");
			this.refBroadcaster = (ActorRef) message;
			// Send join message to broadcaster
			refBroadcaster.tell("join", getSelf());
		} else if (message instanceof String) {
				log.info("["+getSelf().path().name()+"] has received from ["+ getSender().path().name() +"]:" + (String) message);
		}
	}
}
