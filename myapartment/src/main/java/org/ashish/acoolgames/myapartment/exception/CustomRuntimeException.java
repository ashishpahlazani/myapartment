package org.ashish.acoolgames.myapartment.exception;

public abstract class CustomRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3849856667518195390L;

	public CustomRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}
