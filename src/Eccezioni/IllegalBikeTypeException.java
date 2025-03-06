package Eccezioni;

public class IllegalBikeTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2825754671565240633L;
	
	public IllegalBikeTypeException() {
		super();
	}
	
	public IllegalBikeTypeException(String message) {
		super(message);
	}

}
