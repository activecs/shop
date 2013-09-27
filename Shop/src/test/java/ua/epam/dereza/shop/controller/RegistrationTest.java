package ua.epam.dereza.shop.controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.UserDAOImpl;
import ua.epam.dereza.shop.service.CaptchaServiceCookie;
import ua.epam.dereza.shop.service.CaptchaServiceSession;
import ua.epam.dereza.shop.util.Cryptographer;

public class RegistrationTest {

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

	Registration regServ;
	String captcha = "ZXCVB";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doReturn("asdf").when(request).getParameter("firstName");
		doReturn("asdf").when(request).getParameter("lastName");
		doReturn("asdf@mail.ru").when(request).getParameter("inputEmail");
		doReturn("123456").when(request).getParameter("password");
		doReturn("01-01-2010").when(request).getParameter("birthDate");
		doReturn(null).when(request).getParameter("company");
		doReturn("str apr").when(request).getParameter("address1");
		doReturn("str apr").when(request).getParameter("address2");
		doReturn("city").when(request).getParameter("city");
		doReturn("12345").when(request).getParameter("postcode");
		doReturn("asdf").when(request).getParameter("aditionalInfo");
		doReturn("345678").when(request).getParameter("phone");

		doReturn(Cryptographer.encode(captcha)).when(session).getAttribute("expectedCaptcha");
		doReturn(Cryptographer.encode(captcha)).when(request).getParameter("expectedCaptcha");

		doReturn(context).when(config).getServletContext();
		doReturn(new CaptchaServiceSession()).when(context).getAttribute(Constants.CAPTCHA_SERVICE);
		doReturn(new UserDAOImpl()).when(context).getAttribute(Constants.USER_DAO);

		doReturn(context).when(request).getServletContext();
		doReturn(session).when(request).getSession();
		doReturn(captcha).when(request).getParameter("captcha");
		doReturn(dispatcher).when(request).getRequestDispatcher(anyString());
		regServ =  new Registration();
		regServ.init(config);
	}

	@After
	public void tearDown() throws Exception {
		reset(request, response, session, context, dispatcher, config);
	}

	@Test
	public void sendRedirectTest() throws IOException, ServletException {
		regServ.doPost(request, response);
		verify(response).sendRedirect(anyString());
	}

	@Test
	public void cookieCaptchaTest() throws IOException, ServletException {
		doReturn(Constants.CAPTCHA_MODE_COOKIE).when(context).getInitParameter("captchaMode");
		doReturn(new CaptchaServiceCookie()).when(context).getAttribute(Constants.CAPTCHA_SERVICE);

		regServ.doPost(request, response);
		verify(response).sendRedirect(anyString());
	}

	@Test
	public void wrongCaptchaModeTest() throws ServletException, IOException{
		doReturn("sess").when(context).getInitParameter("captchaMode");
		regServ.doPost(request, response);
		verify(response).sendRedirect(anyString());
	}

	@Test
	public void wrongCaptchaTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("it is wrong captcha").when(session).getAttribute("expectedCaptcha");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongFirstnameAndLastnameTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("a").when(request).getParameter("firstName");
		doReturn(null).when(request).getParameter("lastName");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongEmailTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn(null).when(request).getParameter("inputEmail");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongPasswordTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("12345").when(request).getParameter("password");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongDateTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("01-13-2011").when(request).getParameter("birthDate");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongAddressTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("st").when(request).getParameter("address1");
		doReturn("st").when(request).getParameter("address2");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongCityTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}

		doReturn("la-la-la").when(request).getParameter("city");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongPostcodeTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("01234567890").when(request).getParameter("postcode");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongPhoneTest() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("-345678").when(request).getParameter("phone");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void addSameUserTwice() throws ServletException, IOException{
		class IsListOfErrors extends ArgumentMatcher<List>{
			@Override
			public boolean matches(Object list) {
				return ((List<String>) list).size() == 1;
			}
		}
		regServ.doPost(request, response);
		verify(response).sendRedirect(anyString());

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"), argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

}
