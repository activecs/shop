package ua.kharkiv.epam.dereza.socket;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.kharkiv.epam.dereza.bean.Router;

public class ClientTest {

	@Mock
	RestrictedShopService service;

	SocketServer server;
	int port = 3000;
	SocketClient client;
	ClientHandlerFactory factory;
	Router r1;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		factory = new ClientHandlerFactory(service);
		server = new SocketServer(port, factory);
		client = new SocketClient();
		client.connect();

		r1 = new Router("1", 1., 1, new BigDecimal(1.), "1", "1");
		doReturn(1).when(service).countGoodInShop();
		doReturn(r1).when(service).foundGoodByModel("1");

	}

	@After
	public void tearDown() throws Exception {
		server.stopServer();
		server = null;
		client = null;
	}

	@Test
	public void getCountTest() throws IOException {
		client.readFromSocket();
		client.writeToSocket("get count");
		assertEquals("1", client.readFromSocket());
	}

	@Test
	public void getItemTest() throws IOException{
		client.readFromSocket();
		client.writeToSocket("get item=1");
		assertEquals("1|1", client.readFromSocket());
	}

}
