package ua.kharkiv.epam.dereza.socket;

import java.net.Socket;

/**
 * HandlerFactory implementation for ClientHandler
 * 
 * @author Eduard_Dereza
 *
 */
public class ClientHandlerFactory implements HandlerFactory{

	private RestrictedShopService service;

	public ClientHandlerFactory(RestrictedShopService service) {
		this.service = service;
	}

	@Override
	public Runnable createHandler(Socket socket) {
		return new ClientHandler(socket, service);
	}

}
