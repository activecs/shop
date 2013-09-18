package ua.kharkiv.epam.dereza.socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.kharkiv.epam.dereza.bean.Router;


public class ServerTest {

	@Mock
	RestrictedShopService shopService;

	BufferedWriter socketWriter;
	BufferedReader socketReader;
	SocketServer socketServer;
	Socket socket;
	ClientHandlerFactory factory;
	int port = 3000;

	@Before
	public void setUp() throws SocketException {
		MockitoAnnotations.initMocks(this);
		factory = new ClientHandlerFactory(shopService);
		socketServer = new SocketServer(port, factory);
		doReturn(10).when(shopService).countGoodInShop();
		doReturn(new Router("wr642", 5, 2, new BigDecimal(100.), "a", "os"))
		.when(shopService).foundGoodByModel("wr642");
	}

	@After
	public void tearDown() throws IOException {
		reset(shopService);
		socket.close();
		socket = null;
		socketServer.stopServer();
		socketServer = null;
	}

	@Test(timeout=2000)
	public void countGoodTest() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", port);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		socketWriter.append("get count");
		socketWriter.newLine();
		socketWriter.flush();

		//skip welcome letter
		socketReader.readLine();
		assertEquals("10", socketReader.readLine());
	}

	@Test(timeout = 2000)
	public void foundGoodByModelTest() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", port);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		socketWriter.append("get item=wr642");
		socketWriter.newLine();
		socketWriter.flush();

		//skip welcome letter
		socketReader.readLine();
		assertEquals("wr642|100", socketReader.readLine());
	}

	@Test(timeout = 2000)
	public void cannotFindGoodTest() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", port);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		socketWriter.append("get item=wr");
		socketWriter.newLine();
		socketWriter.flush();

		//skip welcome letter
		socketReader.readLine();
		assertEquals("Cannot found equipment with given model", socketReader.readLine());
	}

	@Test(timeout = 2000)
	public void unknownCommandTest() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", port);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		socketWriter.append("unknown command");
		socketWriter.newLine();
		socketWriter.flush();

		//skip welcome letter
		socketReader.readLine();
		assertTrue(new String(socketReader.readLine()).startsWith("Cannot parse command"));
	}

	@Test(timeout = 2000)
	public void cannotParseModelTest() throws UnknownHostException, IOException {
		socket = new Socket("127.0.0.1", port);
		socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		socketWriter.append("get item=-");
		socketWriter.newLine();
		socketWriter.flush();

		//skip welcome letter
		socketReader.readLine();
		assertTrue(new String(socketReader.readLine()).startsWith("Cannot parse model"));
	}
}
