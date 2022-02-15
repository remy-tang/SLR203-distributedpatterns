package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ActorCreator extends UntypedAbstractActor {

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Private counter
	private int counter = 1;

	// Created actors
	private ArrayList<ActorRef> myActors = new ArrayList<>();

	public ActorCreator() {
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(ActorCreator.class, () -> {
			return new ActorCreator();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof CreateMessage) {
			log.info("[" + getSelf().path().name() + "] has received from [" + getSender().path().name() + "]: CREATE");

			// Add new actor to ArrayList of created actors
			myActors.add(getContext().actorOf(SimpleActor.createActor(), "actor" + counter));
			log.info("[" + getSelf().path().name() + "] has created actor" + counter);
			counter++;
		}

		log.info(getContext().actorSelection("/*").toString());
	}
}
