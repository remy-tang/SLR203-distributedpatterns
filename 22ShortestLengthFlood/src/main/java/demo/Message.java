package demo;

public class Message {

	private final int sequenceNumber;
	private final String content;
	private int length;

	public Message(int sequenceNumber, String content, int length) {
		this.sequenceNumber = sequenceNumber;
		this.content = content;
		this.length = length;
	}
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public String getContent() {
		return content;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "[Seq: " + sequenceNumber + ", Length: " + this.length +"] " +  content;
	}
}
