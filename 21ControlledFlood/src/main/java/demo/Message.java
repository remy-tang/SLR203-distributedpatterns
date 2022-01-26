package demo;

public class Message {

	private final int sequenceNumber;
	private final String content;

	public Message(int sequenceNumber, String content) {
		this.sequenceNumber = sequenceNumber;
		this.content = content;
	}
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return "[" + sequenceNumber + "] " +  content;
	}
}
