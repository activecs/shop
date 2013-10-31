package ua.epam.dereza.shop.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
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

import ua.epam.dereza.shop.bean.LoginForm;
import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.ConnectionManager;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.service.UserServiceImpl;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ConnectionManager.class)
@PowerMockIgnore({"org.apache.log4j.*", "ua.epam.dereza.shop.util.Cryptogpapher"})
public class LoginTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	@Mock
	ServletContext context;
	@Mock
	ServletConfig config;
	@Mock
	RequestDispatcher dispatcher;
	@Mock
	UserDAO userDAO;
	@Mock
	DAOFactory daoFactory;
	@Mock
	Connection connection;

	Cookie[] cookies;
	String captcha = "ZXCVB";
	User testUser;
	Login loginController = new Login();


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(ConnectionManager.class);
		PowerMockito.when(ConnectionManager.getConnection()).then(new Answer<Connection>() {
			@Override
			public Connection answer(InvocationOnMock invocation) throws Throwable {
				return connection;
			}
		});

		doReturn(context).when(config).getServletContext();
		doReturn(session).when(request).getSession();
		doReturn(dispatcher).when(request).getRequestDispatcher(anyString());

		testUser = new User();
		testUser.setEmail("testuser@gmail.com");
		testUser.setPassword("24c9e15e52afc47c225b757e7bee1f9d");
		testUser.setEnabled(true);
		testUser.setRole(User.Role.USER);
		doReturn(testUser).when(userDAO).findUserByEmail(isA(Connection.class), eq(testUser.getEmail()));
		doReturn(userDAO).when(daoFactory).getUserDAO();

		cookies = new Cookie[0];
		doReturn(cookies).when(request).getCookies();
		
		doReturn("/login").when(request).getServletPath();
		doReturn("/Shop").when(context).getContextPath();
		String referer = "/account";
		doReturn(referer).when(request).getHeader(Constants.HEADER_REFERER);

		loginController.init(config);
		loginController.userService = new UserServiceImpl(daoFactory);
	}

	@After
	public void tearDown() throws Exception {
		reset(request, response, session, context, dispatcher, config, connection, userDAO);
	}

	@Test
	public void loginTest() throws IOException, ServletException {
		doReturn(testUser.getEmail()).when(request).getParameter(Login.FORM_FIELD_EMAIL);
		doReturn("user1").when(request).getParameter(Login.FORM_FIELD_PASSWORD);

		loginController.doPost(request, response);
		verify(response).sendRedirect(anyString());
	}

	@Test
	public void wrongPasswordTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn(testUser.getEmail()).when(request).getParameter(Login.FORM_FIELD_EMAIL);
		doReturn("user").when(request).getParameter(Login.FORM_FIELD_PASSWORD);

		loginController.doPost(request, response);
		verify(session).setAttribute(eq("errors"),argThat(new IsListOfErrors()));
		verify(session).setAttribute(eq("formBean"), notNull(LoginForm.class));
		verify(response).sendRedirect(anyString());;
	}

	@Test
	public void wrongLoginTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("user34565645@yandex.ru").when(request).getParameter(Login.FORM_FIELD_EMAIL);
		doReturn("user1").when(request).getParameter(Login.FORM_FIELD_PASSWORD);

		loginController.doPost(request, response);
		verify(session).setAttribute(eq("errors"),argThat(new IsListOfErrors()));
		verify(session).setAttribute(eq("formBean"), notNull(LoginForm.class));
		verify(response).sendRedirect(anyString());
	}

	@Test
	public void emptyCredentialTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("").when(request).getParameter(Login.FORM_FIELD_EMAIL);
		doReturn("user1").when(request).getParameter(Login.FORM_FIELD_PASSWORD);

		loginController.doPost(request, response);
		verify(session).setAttribute(eq("errors"),argThat(new IsListOfErrors()));
		verify(session).setAttribute(eq("formBean"), notNull(LoginForm.class));
		verify(response).sendRedirect(anyString());;
	}

	@Test
	public void setRefererTest() throws ServletException, IOException {
		class headerCookie extends ArgumentMatcher<Cookie> {
			@Override
			public boolean matches(Object argument) {
				return ((Cookie)argument).getName().equals(Constants.HEADER_REFERER);
			}
		}
		
		doReturn(testUser.getEmail()).when(request).getParameter(Login.FORM_FIELD_EMAIL);
		doReturn("user1").when(request).getParameter(Login.FORM_FIELD_PASSWORD);

		loginController.doPost(request, response);
		verify(response).addCookie(argThat(new headerCookie()));
	}
	
	@Test
	public void testBanUser() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn(testUser.getEmail()).when(request).getParameter(Login.FORM_FIELD_EMAIL);
		doReturn("user").when(request).getParameter(Login.FORM_FIELD_PASSWORD);

		assertTrue(testUser.getEnabled() == true);
		assertEquals(testUser.getLoginAttemptCount(), 0);
		for(int i=0; i<5; i++){
			loginController.doPost(request, response);
		}
		assertTrue(testUser.getEnabled() == false);
		assertEquals(testUser.getLoginAttemptCount(), 0);
	}
}
