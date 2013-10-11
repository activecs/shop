package ua.epam.dereza.shop.controller;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;
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
import org.mockito.Spy;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dao.UserDAOImpl;
import ua.epam.dereza.shop.db.dto.UserDTO;
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
	@Spy
	Registration regServ = new Registration();

	String captcha = "ZXCVB";
	UserDAO userDAO;
	Integer captchaLifetime = 1000;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		userDAO = new UserDAOImpl();
		doReturn(userDAO).when(context).getAttribute(Constants.USER_DAO);

		doReturn("asdf").when(request).getParameter("firstName");
		doReturn("asdf").when(request).getParameter("lastName");
		doReturn("asdf@mail.ru").when(request).getParameter("inputEmail");
		doReturn("123456").when(request).getParameter("password");
		doReturn("01-01-2010").when(request).getParameter("birthDate");
		doReturn("my company").when(request).getParameter("company");
		doReturn("str apr").when(request).getParameter("address1");
		doReturn("str apr").when(request).getParameter("address2");
		doReturn("city").when(request).getParameter("city");
		doReturn("12345").when(request).getParameter("postcode");
		doReturn("asdf").when(request).getParameter("aditionalInfo");
		doReturn("345678").when(request).getParameter("phone");

		doReturn(Cryptographer.encode(captcha)).when(session).getAttribute(Constants.CAPTCHA_ID);
		doReturn(Cryptographer.encode(captcha)).when(request).getParameter("expectedCaptcha");
		doReturn(captcha).when(request).getParameter("captcha");

		doReturn(context).when(config).getServletContext();
		doReturn(new CaptchaServiceSession(captchaLifetime)).when(context).getAttribute(Constants.CAPTCHA_SERVICE);
		doReturn(Constants.CAPTCHA_MODE_SESSION).when(context).getInitParameter(Constants.CAPTCHA_MODE);

		doReturn(context).when(request).getServletContext();
		doReturn(session).when(request).getSession();
		doReturn(dispatcher).when(request).getRequestDispatcher(anyString());

		regServ.init(config);
	}

	@After
	public void tearDown() throws Exception {
		reset(request, response, session, context, dispatcher, config);
	}

	@Test
	public void cookieCaptchaTest() throws IOException, ServletException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// one error -> captcha expired
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn(Constants.CAPTCHA_MODE_COOKIE).when(context).getInitParameter(Constants.CAPTCHA_MODE);
		doReturn(new CaptchaServiceCookie(captchaLifetime)).when(context).getAttribute(Constants.CAPTCHA_SERVICE);

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongCaptchaModeTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// one error -> captcha expired
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("sess").when(context).getInitParameter("captchaMode");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongCaptchaTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// one error -> check captcha
				return ((List<String>) list).size() == 1;
			}
		}
		doReturn("it is wrong captcha").when(session).getAttribute(Constants.CAPTCHA_ID);

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongFirstnameAndLastnameTest() throws ServletException,
	IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong name, wrong secondname
				return ((List<String>) list).size() == 3;
			}
		}
		doReturn("a").when(request).getParameter("firstName");
		doReturn(null).when(request).getParameter("lastName");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongEmailTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong email
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn(null).when(request).getParameter("inputEmail");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongPasswordTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong password
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("12345").when(request).getParameter("password");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongDateTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong birthdate
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("01-13-2011").when(request).getParameter("birthDate");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongAddressTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong adress1, wrong address2
				return ((List<String>) list).size() == 3;
			}
		}
		doReturn("st").when(request).getParameter("address1");
		doReturn("st").when(request).getParameter("address2");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongCityTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong city
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("la-la-la").when(request).getParameter("city");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongPostcodeTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong postcode
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("01234567890").when(request).getParameter("postcode");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

	@Test
	public void wrongPhoneTest() throws ServletException, IOException {
		class IsListOfErrors extends ArgumentMatcher<List> {
			@Override
			public boolean matches(Object list) {
				// captcha expired, wrong phone
				return ((List<String>) list).size() == 2;
			}
		}
		doReturn("-345678").when(request).getParameter("phone");

		regServ.doPost(request, response);
		verify(request).setAttribute(eq("errors"),
				argThat(new IsListOfErrors()));
		verify(dispatcher).forward(request, response);
	}

}
