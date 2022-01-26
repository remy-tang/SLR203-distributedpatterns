package demo;

import akka.actor.ActorRef;

public class RequestAndSenderMessage {
	private int id;
	private RequestMessage msg;
	private ActorRef ref;

	public RequestAndSenderMessage(int id, RequestMessage msg, ActorRef ref) {
		this.id = id;
		this.msg = msg;
		this.ref = ref;
	}

	public int getId() {
		return id;
	}

	public RequestMessage getMessage() {
		return msg;
	}

	public ActorRef getRef() {
		return ref;
	}

}
