package Eccezioni;

public class NoFreeSpotException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2195962339821193833L;
	
	public NoFreeSpotException() {
		super();
	}
	
	public NoFreeSpotException(String message) {
		super(message);
	}
	
}
