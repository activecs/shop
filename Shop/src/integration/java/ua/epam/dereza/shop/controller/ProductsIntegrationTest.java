package ua.epam.dereza.shop.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.bean.ProductQueryParam;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.ConnectionManager;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.mysql.MysqlDAOFactory;
import ua.epam.dereza.shop.service.ProductService;
import ua.epam.dereza.shop.service.ProductServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionManager.class)
@PowerMockIgnore("org.apache.log4j.*")
public class ProductsIntegrationTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	ServletContext context;
	@Mock
	ServletConfig config;
	@Mock
	RequestDispatcher dispatcher;

	DAOFactory daoFactory = new MysqlDAOFactory();

	ProductService productService;
	Products products;

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

		productService = new ProductServiceImpl(daoFactory);

		doReturn(context).when(config).getServletContext();
		doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
		doReturn(productService).when(context).getAttribute(Constants.SERVICE_PRODUCT);

		products = new Products();
		products.init(config);
	}

	@After
	public void tearDown() throws Exception {
		reset(request,response,context,config);
		products = null;
	}

	@Test
	public void emptyRequestTest() throws ServletException, IOException {
		class ProductsList extends ArgumentMatcher<List<Product>> {
			@Override
			public boolean matches(Object argument) {
				return ((List<Product>) argument).size() == products.ITEM_PER_PAGE_DEFAULT;
			}

		}
		products.doGet(request, response);
		verify(request).setAttribute(eq(Constants.FORM_BEAN), notNull(ProductQueryParam.class));
		verify(request).setAttribute(eq(Constants.BEAN_PRODUCTS), argThat(new ProductsList()));
	}

	@Test
	public void filterTest() throws ServletException, IOException{
		class ProductsList extends ArgumentMatcher<List<Product>> {
			@Override
			public boolean matches(Object argument) {
				List<Product> expectedList = (List<Product>) argument;
				Product expectedProd = expectedList.get(0);
				return expectedList.size() == 1 && expectedProd.getName().equalsIgnoreCase("RT-N14U");
			}

		}
		doReturn("name").when(request).getParameter(Products.FORM_FIELD_SORT_BY);
		doReturn(new String[]{"Asus"}).when(request).getParameterValues(Products.FORM_FIELD_MANUFACTURERS);
		doReturn("wireless router").when(request).getParameter(Products.FORM_FIELD_CATEGORY);
		doReturn("300").when(request).getParameter(Products.FORM_FIELD_PRICE_FROM);
		doReturn("400").when(request).getParameter(Products.FORM_FIELD_PRICE_TO);
		doReturn("n14").when(request).getParameter(Products.FORM_FIELD_KEYWORD);

		products.doGet(request, response);
		verify(request).setAttribute(eq(Constants.FORM_BEAN), notNull(ProductQueryParam.class));
		verify(request).setAttribute(eq(Constants.BEAN_PRODUCTS), argThat(new ProductsList()));
	}
}
