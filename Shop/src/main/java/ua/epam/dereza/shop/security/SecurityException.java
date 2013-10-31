package ua.epam.dereza.shop.security;

/**
 * Signals that an security exception of some sort has occurred.
 * 
 * @author Eduard_Dereza
 *
 */
public class SecurityException extends Exception{

	public SecurityException() {}

	public SecurityException(String message){
		super(message);
	}

	public SecurityException(Throwable throwable){
		super(throwable);
	}

	public SecurityException(String message, Throwable throwable){
		super(message, throwable);
	}
}
