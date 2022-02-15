package demo;

import java.time.Duration;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

public class Requester extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Actor reference
	private ActorRef refB;

	// Timeout
	Timeout timeout = Timeout.create(Duration.ofSeconds(5));

	public Requester() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Requester.class, () -> {
			return new Requester();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof ActorRef) {
			if (refB == null) {
				this.refB = (ActorRef) message;
			}

			// Send 5 messages to B with incremental IDs
			RequestMessage newMessage;
			for (int i = 0; i < 5; i++) {
				newMessage = new RequestMessage(i, "This is a request.");

				Future<Object> future = Patterns.ask(refB, newMessage, timeout);
				log.info("[" + getSelf().path().name() + "] sent request to [" + refB + "]: ID " + newMessage.getId()
						+ ", message " + newMessage.getMessage());

				ResponseMessage resp = (ResponseMessage) Await.result(future, timeout.duration());
				log.info("[" + getSelf().path().name() + "] received response from [b]: ID " + resp.getId()
						+ ", message " + resp.getMessage());
			}
		}
	}
}