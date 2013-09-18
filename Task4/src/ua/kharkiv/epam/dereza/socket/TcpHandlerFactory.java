package ua.kharkiv.epam.dereza.socket;

import java.net.Socket;

/**
 * HandlerFactory implementation for TcpHandler
 * 
 * @author Eduard_Dereza
 *
 */
public class TcpHandlerFactory implements HandlerFactory{

	@Override
	public Runnable createHandler(Socket socket) {
		return new TcpHandler(socket);
	}

}
