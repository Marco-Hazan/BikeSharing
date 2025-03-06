package Eccezioni;

public class MorsaSbagliataException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1834637336673639378L;
	
	public MorsaSbagliataException() {
		super();
	}
	
	public MorsaSbagliataException(String message) {
		super(message);
	}

}
