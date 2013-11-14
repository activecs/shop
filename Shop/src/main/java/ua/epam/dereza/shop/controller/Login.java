package ua.epam.dereza.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.LoginForm;
import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.UserService;

/**
 * Log in controller
 * 
 * @author Eduard_Dereza
 * 
 */
@WebServlet("/login")
public class Login extends HttpServlet {

	private static final long serialVersionUID = -8763062434586925599L;
	private static final Logger log = Logger.getLogger(Login.class);
	// form's fields
	public static final String FORM_FIELD_EMAIL = "inputEmail";
	public static final String FORM_FIELD_PASSWORD = "password";

	UserService userService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userService = (UserService) config.getServletContext().getAttribute(Constants.SERVICE_USER);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * if login form has errors we use pattern post-redirect-get and session
		 * contains previous authorization errors
		 */
		HttpSession session = request.getSession();
		List<String> errors = (List<String>) session.getAttribute(Constants.BEAN_ERRORS);
		LoginForm formBean = (LoginForm) session.getAttribute(Constants.FORM_BEAN);
		if (errors != null && formBean != null) {
			session.removeAttribute(Constants.BEAN_ERRORS);
			session.removeAttribute(Constants.FORM_BEAN);

			request.setAttribute(Constants.BEAN_ERRORS, errors);
			request.setAttribute(Constants.FORM_BEAN, formBean);
		}
		request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		LoginForm formBean = new LoginForm();
		User userDTO = null;

		parseRequest(request, formBean);
		if (log.isTraceEnabled())
			log.trace("User tried to login ->" + formBean);

		String referer = getReferer(request, response);

		try {
			UserService.AuthState authState = userService.authentificate(
					formBean.getEmail(), formBean.getPassword());
			String redirect = null;

			if (authState.equals(UserService.AuthState.OK)) {
				userDTO = userService.findUserByEmail(formBean.getEmail());
				log.info("Logged in ->" + userDTO);
				request.getSession().setAttribute(Constants.BEAN_USER, userDTO);
				
				redirect = response.encodeRedirectURL(referer);
				
				log.debug("send redirect to ->" + redirect);
				response.sendRedirect(redirect);
				return;
			}

			if (authState.equals(UserService.AuthState.BANNED))
				errors.add("Your account is banned, try again later");

			if (authState.equals(UserService.AuthState.INCORRECT))
				errors.add("Check your login or password");

			redirect = getServletContext().getContextPath()	+ request.getServletPath();
			request.getSession().setAttribute(Constants.BEAN_ERRORS, errors);
			request.getSession().setAttribute(Constants.FORM_BEAN, formBean);

			log.debug("redirect to ->" + redirect);
			response.sendRedirect(response.encodeRedirectURL(redirect));
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Parses login form
	 * 
	 * @param request
	 * @param formBean
	 */
	private void parseRequest(HttpServletRequest request, LoginForm formBean) {
		formBean.setEmail(request.getParameter(FORM_FIELD_EMAIL));
		formBean.setPassword(request.getParameter(FORM_FIELD_PASSWORD));
	}

	/**
	 * Gets referer from cookie and sets referer to cookie
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	private String getReferer(HttpServletRequest req, HttpServletResponse res) {
		String referer = null;
		for (Cookie cookie : req.getCookies()) {
			if (cookie.getName().equals(Constants.HEADER_REFERER)) {
				referer = cookie.getValue();
				break;
			}
		}
		if (referer == null) {
			String headerRef = req.getHeader(Constants.HEADER_REFERER);
			// if referer contains /login we will set referer to / - root of app
			if (headerRef.contains(req.getServletPath())) {
				referer = getServletContext().getContextPath();
			} else {
				referer = headerRef;
				Cookie rfr = new Cookie(Constants.HEADER_REFERER, referer);
				rfr.setHttpOnly(true);
				rfr.setPath(getServletContext().getContextPath());
				rfr.setMaxAge(300);
				res.addCookie(rfr);
			}
		}

		return referer;
	}
}
