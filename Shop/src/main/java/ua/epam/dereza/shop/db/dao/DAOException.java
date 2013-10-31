package ua.epam.dereza.shop.db.dao;

/**
 * Signals that an DAO exception of some sort has occurred.
 * 
 * @author Eduard_Dereza
 *
 */
public class DAOException extends Exception{

	public DAOException() {}

	public DAOException(String message){
		super(message);
	}

	public DAOException(Throwable throwable){
		super(throwable);
	}

	public DAOException(String message, Throwable throwable){
		super(message, throwable);
	}
}
