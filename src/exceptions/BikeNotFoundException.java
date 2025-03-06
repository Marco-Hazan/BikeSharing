package exceptions;

public class BikeNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7029606818938076300L;
	
	public BikeNotFoundException() {
		super();
	}
	
	public BikeNotFoundException(String message) {
		super(message);
	}

}
