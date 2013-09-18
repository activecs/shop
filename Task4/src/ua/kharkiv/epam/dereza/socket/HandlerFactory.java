package ua.kharkiv.epam.dereza.socket;

import java.net.Socket;

/**
 * Basic interface for abstract factory
 * 
 * @author Eduard_Dereza
 *
 */
public interface HandlerFactory {

	public Runnable createHandler(Socket socket);

}
