package ua.epam.dereza.shop.filter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.security.SecurityRule;

@RunWith(Parameterized.class)
public class SecurityFilterParameterizedTest {

	@Mock
	HttpServletRequest request;
	@Mock
	ServletContext context;

	SecurityFilter filter;
	SecurityRule rule;
	String uri;
	boolean isUriProtected;
	boolean hasPermission;
	User userBean;
	String message;

	public SecurityFilterParameterizedTest(String message,SecurityRule rule,String uri,User userBean,boolean isUriProtected,boolean hasPermission) {
		this.message = message;
		this.rule = rule;
		this.uri = uri;
		this.isUriProtected = isUriProtected;
		this.hasPermission = hasPermission;
		this.userBean = userBean;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Set<User.Role> userAdmin = new HashSet<>();
		Set<User.Role> user = new HashSet<>();
		Set<User.Role> admin = new HashSet<>();
		userAdmin.add(User.Role.USER);
		userAdmin.add(User.Role.ADMIN);
		user.add(User.Role.USER);
		admin.add(User.Role.ADMIN);

		User userBean = new User();
		userBean.setRole(User.Role.USER);
		User adminBean = new User();
		adminBean.setRole(User.Role.ADMIN);

		Object[][] data = new Object[][] {
				{"Rule for /account, USER,ADMIN, tried to gain access USER to /account and failed",new SecurityRule("/account", userAdmin), "/account", userBean, true, true},
				{"Rule for .*\\.jsp, USER tried to gain access ADMIN to /index.jsp and got it",new SecurityRule(".*\\.jsp", user), "/index.jsp", adminBean, true, false},
				{"Rule for /.*, USER tried to gain access USER to /index.jsp and failed",new SecurityRule("/.*", user), "/index.jsp", userBean, true, true},
				{"Rule for .*\\.jsp, USER tried to gain access ADMIN to / and failed",new SecurityRule(".*\\.jsp", user), "/", adminBean, false, false},
				{"Rule for /account, USER, tried to gain access USER to /account/la and failed",new SecurityRule("/account", user), "/account/la", userBean, false, true}
		};

		return Arrays.asList(data);
	}


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doReturn("").when(context).getContextPath();

		filter = new SecurityFilter();
		filter.context = context;
	}

	@After
	public void tearDown() throws Exception {
		reset(context);
		filter = null;
		rule = null;
		uri = null;
		userBean = null;
	}

	@Test
	public void checkUriCoincidenceTest() {
		assertEquals(message, isUriProtected, filter.checkUriCoincidence(rule, uri));

		if(isUriProtected)
			assertEquals(hasPermission, filter.hasPermission(rule, userBean));
	}

}
