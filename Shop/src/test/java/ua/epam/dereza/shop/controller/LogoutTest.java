package ua.epam.dereza.shop.controller;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LogoutTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	@Mock
	ServletConfig config;
	@Mock
	ServletContext context;

	Logout logoutServ = new Logout();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doReturn(session).when(request).getSession();
		doReturn(context).when(config).getServletContext();

		logoutServ.init(config);
	}

	@After
	public void tearDown() throws Exception {
		reset(request, response, session);
	}

	@Test
	public void logoutTest() throws ServletException, IOException {
		logoutServ.doGet(request, response);
		verify(session).invalidate();
		verify(response).sendRedirect(anyString());
	}

}
