package ua.kharkiv.epam.dereza.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Simple client for shop via socket
 * 
 * @author Eduard_Dereza
 *
 */
public class SocketClient {

	private int port = 3000;
	private String address = "127.0.0.1";
	private Socket socket;
	private BufferedReader socketReader;
	private BufferedWriter socketWriter;
	private BufferedReader keyboard;

	public SocketClient() {}

	public SocketClient(int port, String address) {
		this.port = port;
		this.address = address;
	}

	public void connect() throws UnknownHostException, IOException{
		socket = new Socket(address, port);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	public void startSession() throws UnknownHostException, IOException{
		if (socket == null){
			connect();
		}
		keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(readFromSocket());

		String line = new String();
		System.out.print(">");
		line = keyboard.readLine();
		writeToSocket(line);
		System.out.println(readFromSocket());

		socket.close();
	}

	/**
	 * Allows to read from socket
	 * 
	 * @throws IOException
	 */
	public String readFromSocket() throws IOException{
		String result = "";
		do {
			result += socketReader.readLine();;
		} while (socketReader.ready());
		return result;
	}

	public void writeToSocket(String line) throws IOException{
		socketWriter.write(line);
		socketWriter.newLine();
		socketWriter.flush();
	}

	/**
	 * Enter point
	 * 
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		SocketClient sc = new SocketClient();
		sc.connect();
		sc.startSession();
	}
}
