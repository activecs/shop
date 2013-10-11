package ua.epam.dereza.shop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.LoginForm;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.service.LoginService;
import ua.epam.dereza.shop.service.LoginServiceImpl;

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

	private LoginService loginService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		loginService = new LoginServiceImpl((UserDAO) config.getServletContext().getAttribute(Constants.USER_DAO));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String referer = request.getHeader(Constants.HEADER_REFERER);
		log.trace("Setted referer->" + referer);
		request.getSession().setAttribute(Constants.HEADER_REFERER, referer);

		request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		LoginForm formBean = new LoginForm();
		UserDTO userDTO = null;

		parseRequest(request, formBean);
		if(log.isEnabledFor(Level.TRACE))
			log.trace("User tried to login ->" + formBean);

		if(!loginService.authentificate(formBean.getEmail(), formBean.getPassword()))
			errors.add("Check your login or password");

		if(errors.isEmpty()){
			userDTO = loginService.signIn(formBean.getEmail(), formBean.getPassword());
			log.info("Logged in ->" + userDTO);
			request.getSession().setAttribute(Constants.USER_BEAN, userDTO);

			String referer = (String)request.getSession().getAttribute(Constants.HEADER_REFERER);
			String redirect = null;
			if(referer != null){
				redirect = response.encodeRedirectURL(referer);
				request.getSession().removeAttribute(Constants.HEADER_REFERER);
			}else{
				redirect = response.encodeRedirectURL(getServletContext().getContextPath());
			}

			log.debug("send redirect to ->" + redirect);
			response.sendRedirect(redirect);
		}else{
			log.debug("forward to ->" + Constants.PAGE_LOGIN);
			request.setAttribute("errors", errors);
			request.setAttribute("formBean", formBean);
			request.getRequestDispatcher(response.encodeURL(Constants.PAGE_LOGIN)).forward(request, response);
		}

	}

	/**
	 * Parses login form
	 * 
	 * @param request
	 * @param formBean
	 */
	private void parseRequest(HttpServletRequest request, LoginForm formBean){
		formBean.setEmail(request.getParameter(FORM_FIELD_EMAIL));
		formBean.setPassword(request.getParameter(FORM_FIELD_PASSWORD));
	}

}
