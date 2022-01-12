package demo;

public class RequestMessage {
	
	private int id;
	private String message;
	
	public RequestMessage(int id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public int getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
}
