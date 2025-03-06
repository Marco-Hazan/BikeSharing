package Eccezioni;

public class RoofReachedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2632046555110076138L;

	public RoofReachedException() {
		super();
	}
	
	public RoofReachedException(String message) {
		super(message);
	}
}
