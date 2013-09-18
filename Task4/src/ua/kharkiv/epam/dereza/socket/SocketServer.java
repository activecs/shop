package ua.kharkiv.epam.dereza.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * Socket server, run by Main.java(console shop)
 * 
 * @author Eduard_Dereza
 *
 */
public class SocketServer extends Thread {

	private static final Logger log = Logger.getLogger(SocketServer.class);

	// default port 3000
	private int port = 80;
	private int maxBacklog = 10;
	private ServerSocket ss;
	private Socket socket;
	private ExecutorService execService;
	private HandlerFactory factory;

	public SocketServer(HandlerFactory factory) {
		this.factory = factory;
		execService = Executors.newCachedThreadPool();
		this.setDaemon(true);
		start();
	}

	public SocketServer(int port, HandlerFactory factory) {
		this.port = port;
		this.factory = factory;
		execService = Executors.newCachedThreadPool();
		this.setDaemon(true);
		start();
	}

	@Override
	public void run() {
		try {
			ss = new ServerSocket(port, maxBacklog);
			log.info("Waiting for clients..");
			while (!isInterrupted()) {
				socket = ss.accept();
				socket.setTcpNoDelay(true);
				log.info("Got a client");
				execService.execute(factory.createHandler(socket));
			}
		} catch(SocketException e){
			log.error("server socket was closed by server side", e);
		} catch(IOException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Allows you to stop server
	 * 
	 * @throws IOException
	 */
	public void stopServer() throws IOException{
		log.info("Trying to stop server socket...");
		ss.close();
	}


}
