package demo;

public class ResponseMessage {
	
	private int id;
	private String message;
	
	public ResponseMessage(int id, String message) {
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
