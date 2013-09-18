package ua.kharkiv.epam.dereza.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Simple tcp server for request handling.
 * 
 * Changed after previous code review.
 * I did my best to do this method more readable.
 * 
 * @author Eduard_Dereza
 * 
 */
public class TcpHandler extends Thread {

	private static final Logger log = Logger.getLogger(TcpHandler.class);

	private BufferedReader socketReader;
	private BufferedWriter socketWriter;
	// contains request headers
	private List<String> reqHeaders;
	private Socket socket;
	// contains available operations for online calculator
	private Map<String, Operation> operations;
	// statuses for http responses
	private static final String STATUS_BAD_REQUEST = "400 Bad Request";
	private static final String STATUS_INTERNAL_ERROR = "500 Internal Server Error";
	private static final String STATUS_OK = "200 OK";
	private static final String BASE_DIR = "E:\\";

	public TcpHandler(Socket socket) {
		this.socket = socket;
		initOperations();
	}

	@Override
	public void run() {
		String errorMessage = null;
		try {
			try {
				initSocketStreams();
				readRequestHeader();

				if (isRequestCorrect()) {
					// prints headers
					log.info("HEADER ->" + reqHeaders);

					// sends file according to request, for example
					// host:port/filename.extension
					Pattern pattern = Pattern.compile(" /[\\d\\w)]*\\.[\\d\\w)]*");
					Matcher matcher = pattern.matcher(reqHeaders.get(0));
					if (matcher.find()) {
						String filename = matcher.group().replaceAll(" /", "");
						File file = new File(BASE_DIR + filename);
						String mime = getMime(file);
						sendFile(readFile(file), STATUS_OK, mime);
					} else {

						// calculator
						// gets query from first line of request's header
						log.trace("Will be parsed url ->" + reqHeaders.get(0));
						pattern = Pattern.compile("/cgi/\\S{3,}");
						matcher = pattern.matcher(reqHeaders.get(0));
						matcher.find();
						String query = matcher.group();
						log.debug("query ->" + query);

						// gets name of operation(add, divide,..)
						pattern = Pattern.compile("\\w{3,10}\\?");
						matcher = pattern.matcher(query);
						matcher.find();
						String operation = matcher.group().replaceAll("\\?", "");
						log.debug("operation ->" + operation);
						Operation oper = operations.get(operation);

						// gets required operation from map, parse operands
						pattern = Pattern.compile("\\w+\\d*=\\d+((\\.\\d{1,})|\\d{0,})");
						matcher = pattern.matcher(query);

						// saves operands for operation
						List<String> operands = new ArrayList<String>();
						while (matcher.find())
							operands.add(matcher.group());
						log.trace("operands ->" + operands);
						//perform operation and return response to client
						oper.operate(socketWriter, operands);
					}
				}
			} catch (IllegalStateException ie) {
				errorMessage = "Request doesn't contain parameter - 'file' or correct /cgi/ in url";
				sendError(errorMessage, STATUS_BAD_REQUEST);
				log.error(errorMessage);
			} catch (IOException e) {
				errorMessage = "Occured problems related with i/o";
				sendError(errorMessage, STATUS_INTERNAL_ERROR);
				log.error(errorMessage);
			} catch (Exception exc) {
				log.error("Occured error in oper.operate(writer, operands)", exc);
				sendError("Internal error", STATUS_INTERNAL_ERROR);
			} finally {
				socket.close();
			}
		} catch (Exception ex) {
			log.error(ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Reads file and returns array of byte
	 * 
	 * @param file
	 * @return array of byte
	 * @throws IOException
	 */
	private byte[] readFile(File file) throws IOException {
		if (!file.isFile()) {
			String message = "Problems with-" + file.getName();
			sendError(message, STATUS_INTERNAL_ERROR);
			log.error(message);
		}

		byte[] array = new byte[(int) file.length()];
		FileInputStream ins = new FileInputStream(file);
		ins.read(array);
		ins.close();

		return array;
	}

	/**
	 * Sends occured error to socket
	 * 
	 * @param message
	 * @param status
	 * @throws IOException
	 */
	private void sendError(String message, String status) throws IOException {
		socketWriter.write("HTTP/1.1 " + status + "\n");
		DateFormat df = DateFormat.getTimeInstance();
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		socketWriter.write("Date: " + df.format(new Date()) + "\n");
		socketWriter.write("Server: simpleTCPserver\n");
		socketWriter.write("Connection: close\n");
		socketWriter.write("Content-Type: text/html; charset=UTF-8\n");
		socketWriter.write("Content-Length: " + message.length() + "\n\n");
		socketWriter.write(message + "\n");
		socketWriter.flush();
	}

	/**
	 * Sends file to socket with detected mime-type
	 * 
	 * @param array
	 * @param status
	 * @param mime
	 * @throws IOException
	 */
	private void sendFile(byte[] array, String status, String mime)
			throws IOException {
		socketWriter.write("HTTP/1.1 " + status + "\n");
		DateFormat df = DateFormat.getTimeInstance();
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		socketWriter.write("Last-Modified: " + df.format(new Date()) + "\n");
		socketWriter.write("Server: simpleTCPserver\n");
		socketWriter.write("Connection: close\n");
		socketWriter.write("Content-Type: " + mime + "\n");
		socketWriter.write("Content-Length: " + array.length + "\n\n");
		socketWriter.flush();
		socket.getOutputStream().write(array, 0, array.length);
	}

	/**
	 * Allows you to detect MIME-type according to file's extension
	 * 
	 * @param file
	 * @return the most appropriate MIME-type according to file's extension
	 */
	private String getMime(File file) {
		String mime = null;
		String path = file.toString();
		int index = path.lastIndexOf(".");
		if (index > 0) {
			String ext = path.substring(index);
			if (ext.equalsIgnoreCase(".html"))
				mime = "text/html";
			else if (ext.equalsIgnoreCase(".htm"))
				mime = "text/html";
			else if (ext.equalsIgnoreCase(".log"))
				mime = "text/plain";
			else if (ext.equalsIgnoreCase(".txt"))
				mime = "text/plain";
			else if (ext.equalsIgnoreCase(".gif"))
				mime = "image/gif";
			else if (ext.equalsIgnoreCase(".jpg"))
				mime = "image/jpeg";
			else if (ext.equalsIgnoreCase(".jpeg"))
				mime = "image/jpeg";
			else if (ext.equalsIgnoreCase(".bmp"))
				mime = "image/x-xbitmap";
		}
		if (mime == null)
			mime = "application/zip";

		return mime;
	}

	/**
	 * Reads request, saves headers and skips body
	 * 
	 * @throws IOException
	 */
	private void readRequestHeader() throws IOException {
		reqHeaders = new ArrayList<String>();
		while (socketReader.ready()) {
			String line = socketReader.readLine();
			if (line.length() > 0)
				reqHeaders.add(line);
			else
				break;
		}
	}

	/**
	 * Initializes map of available operations
	 * 
	 */
	private void initOperations(){
		operations = new HashMap<String, Operation>();
		operations.put("add", new AddOperation());
		operations.put("subtract", new SubtractOperation());
		operations.put("multiply", new MultiplyOperation());
		operations.put("divide", new DivideOperation());
	}

	/**
	 * Initializes socket's i/o streams
	 * 
	 * @throws IOException
	 */
	private void initSocketStreams() throws IOException{
		socketReader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream()));
	}

	/**
	 * Checks whether request type is Get
	 * 
	 * @return if request type is GET
	 */
	private boolean isRequestCorrect(){
		if(reqHeaders.isEmpty())
			return false;
		if(!reqHeaders.get(0).contains("GET"))
			return false;

		return true;
	}
}
