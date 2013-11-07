package ua.epam.dereza.shop.service;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.db.dao.ConnectionManager;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.mysql.MysqlDAOFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionManager.class)
public class UserServiceIntegrationTest {

	UserService userService;
	User testUser;
	String notEncodedUserPassword = "user1";

	@BeforeClass
	public static void createTestDb() throws IOException, ClassNotFoundException, SQLException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(new Object().getClass().getResourceAsStream("/initTestDB.sql")));
		StringBuilder query = new StringBuilder();
		while(reader.ready()){
			query.append(reader.readLine());
		}
		Connection conn = getBaseConnection();
		String queries[] = query.toString().split(";");
		Statement stm = conn.createStatement();
		for(String tempQuery : queries){
			stm.addBatch(tempQuery);
		}
		stm.executeBatch();
		conn.commit();
		stm.close();
		stm.close();
	}

	@AfterClass
	public static void removeTestDb() throws ClassNotFoundException, SQLException{
		Connection conn = getBaseConnection();
		Statement stm = conn.createStatement();
		stm.executeUpdate("DROP DATABASE testbase;");
		conn.commit();
		stm.close();
		conn.close();
	}

	private static Connection getBaseConnection() throws ClassNotFoundException, SQLException {
		String driverName = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://epuakhaw0162:3306/";
		Class.forName(driverName);
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root123");
		Connection con = DriverManager.getConnection(connectionUrl, prop);
		con.setAutoCommit(false);
		return con;
	}

	private Connection getConnection() throws ClassNotFoundException, SQLException {
		String driverName = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://epuakhaw0162:3306/testbase";
		Class.forName(driverName);
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root123");
		Connection con = DriverManager.getConnection(connectionUrl, prop);
		con.setAutoCommit(false);
		return con;
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(ConnectionManager.class);
		PowerMockito.when(ConnectionManager.getConnection()).then(
				new Answer<Connection>() {
					@Override
					public Connection answer(InvocationOnMock invocation)
							throws Throwable {
						return getConnection();
					}
				});

		testUser = new User();
		testUser.setEmail("testuser678678@gmail.com");
		testUser.setPassword("24c9e15e52afc47c225b757e7bee1f9d");
		testUser.setAdditionalInfo("");
		testUser.setAddress1("Pushkinskaya str");
		testUser.setAddress2("apr 10");
		testUser.setAvatar("default.png");
		testUser.setBirthDate(new Date());
		testUser.setCity("Kharkiv");
		testUser.setCompany("Home");
		testUser.setEnabled(true);
		testUser.setFirstName("Taras");
		testUser.setLastName("Yoki");
		testUser.setPhone("093345345");
		testUser.setPostCode(567567567);
		testUser.setRole(User.Role.USER);

		DAOFactory daoFacory = new MysqlDAOFactory();
		userService = new UserServiceImpl(daoFacory);
	}

	@After
	public void tearDown() throws Exception {
		userService = null;
		testUser = null;
	}

	@Test
	public void authentificateNotExistinUserTest() throws DAOException {
		UserService.AuthState state = userService.authentificate(testUser.getEmail(), "incorrect");
		assertEquals("Current user doesn't exist", state,UserService.AuthState.INCORRECT);
	}

	@Test
	public void createLoginDeleteUserTest() throws ClassNotFoundException, SQLException,
	DAOException {
		testUser.setPassword(notEncodedUserPassword);
		userService.addUser(testUser);
		assertEquals(UserService.AuthState.OK, userService.authentificate(testUser.getEmail(), notEncodedUserPassword));
		userService.removeUser(testUser.getEmail());
	}
}
