package ua.epam.dereza.shop.filter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.security.SecurityRule;

public class SecurityFilterBasicTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	@Mock
	ServletContext context;
	@Mock
	FilterConfig config;
	@Mock
	RequestDispatcher dispatcher;
	@Mock
	FilterChain chain;

	SecurityFilter filter;
	List<SecurityRule> rules;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Set<User.Role> userAdmin = new HashSet<>();
		Set<User.Role> user = new HashSet<>();
		Set<User.Role> admin = new HashSet<>();
		userAdmin.add(User.Role.USER);
		userAdmin.add(User.Role.ADMIN);
		user.add(User.Role.USER);
		admin.add(User.Role.ADMIN);

		rules = new ArrayList<>();
		rules.add(new SecurityRule("/account", userAdmin));
		rules.add(new SecurityRule("/admin.*", admin));
		rules.add(new SecurityRule("/user.*", user));

		doReturn(session).when(request).getSession();
		doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
		doReturn("/Shop").when(context).getContextPath();
		doReturn(new StringBuffer("http://127.0.0.1:8080/Shop/account")).when(
				request).getRequestURL();

		filter = new SecurityFilter();
		filter.rules = rules;
		filter.context = context;
	}

	@After
	public void tearDown() throws Exception {
		reset(config);
	}

	@Test
	public void permissionDeniedTest() throws IOException, ServletException {
		doReturn("/admin/managment").when(request).getRequestURI();
		User user = new User();
		user.setRole(User.Role.USER);
		doReturn(user).when(session).getAttribute(Constants.BEAN_USER);

		filter.doFilter(request, response, chain);
		verify(response).sendError(eq(403));
	}

	@Test
	public void redirectToLoginTest() throws IOException, ServletException {
		doReturn("/account").when(request).getRequestURI();

		filter.doFilter(request, response, chain);
		verify(request).getRequestDispatcher(eq(Constants.PAGE_LOGIN));
		verify(dispatcher).forward(any(ServletRequest.class),
				any(ServletResponse.class));
	}

	@Test
	public void allowAccessTest() throws IOException, ServletException {
		doReturn("/account").when(request).getRequestURI();
		User user = new User();
		user.setRole(User.Role.USER);
		doReturn(user).when(session).getAttribute(Constants.BEAN_USER);

		filter.doFilter(request, response, chain);
		verify(chain).doFilter(any(ServletRequest.class),
				any(ServletResponse.class));
	}

	@Test
	public void notProtectedPageTest() throws IOException, ServletException {
		doReturn("/notprotected").when(request).getRequestURI();
		User user = new User();
		user.setRole(User.Role.USER);
		doReturn(user).when(session).getAttribute(Constants.BEAN_USER);

		filter.doFilter(request, response, chain);
		verify(chain).doFilter(any(ServletRequest.class),any(ServletResponse.class));
	}

	@Test
	public void notProtectedPageWithoutUserbeanTest() throws IOException, ServletException {
		doReturn("/notprotected").when(request).getRequestURI();

		filter.doFilter(request, response, chain);
		verify(chain).doFilter(any(ServletRequest.class),any(ServletResponse.class));
	}
}
