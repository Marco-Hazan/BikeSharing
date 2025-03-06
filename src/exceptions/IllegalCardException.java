package exceptions;

public class IllegalCardException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8635828419204520519L;
	
	public IllegalCardException() {
		super();
	}
	
	public IllegalCardException(String message) {
		super(message);
	}

}
