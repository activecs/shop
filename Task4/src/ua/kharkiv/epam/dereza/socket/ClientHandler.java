package ua.kharkiv.epam.dereza.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import ua.kharkiv.epam.dereza.bean.NetworkEquipment;

/**
 * Client's handler for requests done via sockets(console shop).
 * 
 * @author Eduard_Dereza
 *
 */
public class ClientHandler extends Thread {

	private static final Logger log = Logger.getLogger(ClientHandler.class);

	private RestrictedShopService service;

	private BufferedReader socketReader;
	private BufferedWriter socketWriter;
	private Socket socket;
	private static final String GET_COUNT_COMMAND = "get count";
	private static final String GET_ITEM_COMMAND = "get item";

	public ClientHandler(Socket socket, RestrictedShopService service) {
		this.setDaemon(true);
		this.socket = socket;
		this.service = service;
	}

	@Override
	public void run() {
		String logMessage = "Client handler started";
		log.debug(logMessage);
		try {
			socketReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			socketWriter = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			printToSocket("You connected to shop via socket, now you can enter command."
					+ "Avaliable commands:'"
					+ GET_COUNT_COMMAND
					+ "', '"
					+ GET_ITEM_COMMAND + "=modelname'");

			String line = socketReader.readLine();
			Pattern pattern = Pattern.compile("get \\w{4,5}");
			Matcher matcher = pattern.matcher(line);
			String command = null;
			if (!matcher.find()){
				logMessage = "Cannot parse command -" + line;
				printToSocket(logMessage);
				log.debug(logMessage);
			}
			else {
				command = matcher.group();
				switch (command) {
				case GET_COUNT_COMMAND: {
					int totalCount = service.countGoodInShop();
					printToSocket(new Integer(totalCount).toString());
					break;
				}
				case GET_ITEM_COMMAND: {
					pattern = Pattern.compile("\\w{1,}$");
					matcher = pattern.matcher(line);
					String model = null;
					if(matcher.find()){
						model = matcher.group();
						NetworkEquipment foundEquip = service
								.foundGoodByModel(model);
						if (foundEquip != null) {
							printToSocket(foundEquip.getModel() + "|"
									+ foundEquip.getPrice());
						} else {
							logMessage = "Cannot found equipment with given model";
							printToSocket(logMessage);
							log.debug(logMessage);
						}
					}else{
						logMessage = "Cannot parse model";
						printToSocket(logMessage);
						log.debug(logMessage);
					}
					break;
				}
				}
			}
		} catch(SocketException e){
			logMessage = "socket was closed";
			log.error(logMessage, e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				socket.close();
			}catch(Exception e){
				logMessage = "socket ia already closed.";
				log.error(logMessage, e);
			}
		}

	}

	/**
	 * Allow to print into socket input stream
	 * 
	 * @param message
	 * @throws IOException
	 */
	private void printToSocket(String message) throws IOException {
		socketWriter.write(message);
		socketWriter.newLine();
		socketWriter.flush();
	}

}
