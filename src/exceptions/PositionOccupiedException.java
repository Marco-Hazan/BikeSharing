package exceptions;

public class PositionOccupiedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9174975532401957446L;

	public PositionOccupiedException() {
		super();
	}
	
	public PositionOccupiedException(String message) {
		super(message);
	}
}
