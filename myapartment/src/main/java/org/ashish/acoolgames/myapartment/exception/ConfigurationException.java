package org.ashish.acoolgames.myapartment.exception;

public class ConfigurationException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3118255572841739775L;

	public ConfigurationException(String message) {
		super(message);
	}
	public ConfigurationException(Exception exception) {
		super(exception);
	}
}
