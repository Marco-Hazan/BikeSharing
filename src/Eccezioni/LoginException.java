package Eccezioni;

public class LoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1246794588804482569L;
	
	public LoginException() {
		super();
	}
	
	public LoginException(String message) {
		super(message);
	}

}
