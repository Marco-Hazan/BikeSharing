package Eccezioni;

public class RackNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3928181364663049970L;
	public RackNotFoundException() {
		super();
	}
	
	public RackNotFoundException(String message) {
		super(message);
	}
}
