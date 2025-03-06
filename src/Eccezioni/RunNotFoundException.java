package Eccezioni;

public class RunNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8363388280276441281L;
	public RunNotFoundException() {
		super();
	}
	
	public RunNotFoundException(String message) {
		super(message);
	}

}
