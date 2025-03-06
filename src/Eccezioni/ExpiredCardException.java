package Eccezioni;

public class ExpiredCardException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3769176655272214009L;
	
	public ExpiredCardException() {
		super();
	}
	
	public ExpiredCardException(String message) {
		super(message);
	}

}
