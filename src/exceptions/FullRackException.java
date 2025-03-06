package exceptions;

public class FullRackException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FullRackException() {
		super();
	}
	
	public FullRackException(String message) {
		super(message);
	}
}
