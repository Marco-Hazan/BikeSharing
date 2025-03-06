package Eccezioni;

public class ActiveRunException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2628784856556965808L;
	
	public ActiveRunException() {
		super();
	}
	
	public ActiveRunException(String message) {
		super(message);
	}

}
