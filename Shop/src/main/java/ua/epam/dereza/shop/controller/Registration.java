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

import ua.epam.dereza.shop.bean.RegistrationForm;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.service.CaptchaService;
import ua.epam.dereza.shop.service.RegistrationServiceImpl;
import ua.epam.dereza.shop.util.BeanTransformer;
import ua.epam.dereza.shop.util.BeanValidator;

/**
 * Registration controller
 * 
 * @author Eduard_Dereza
 * 
 */
@WebServlet(name = "registrationController", urlPatterns = { "/registration" })
public class Registration extends HttpServlet {

	private static final long serialVersionUID = 6860202757191222139L;
	private static final Logger log = Logger.getLogger(Registration.class);
	private RegistrationServiceImpl registrService;
	private CaptchaService captchaService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		captchaService = (CaptchaService) config.getServletContext()
				.getAttribute(Constants.CAPTCHA_SERVICE);
		registrService = new RegistrationServiceImpl((UserDAO) config
				.getServletContext().getAttribute(Constants.USER_DAO));
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(response.encodeURL(Constants.PAGE_REGISTRATION)).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserDTO newUser;
		RegistrationForm formBean = new RegistrationForm();
		List<String> errors = new ArrayList<String>();

		// saves values from form
		formBean.setFirstName(request.getParameter("firstName"));
		formBean.setLastName(request.getParameter("lastName"));
		formBean.setEmail(request.getParameter("inputEmail"));
		formBean.setPassword(request.getParameter("password"));
		formBean.setBirthDate(request.getParameter("birthDate"));
		formBean.setCompany(request.getParameter("company"));
		formBean.setAddress1(request.getParameter("address1"));
		formBean.setAddress2(request.getParameter("address2"));
		formBean.setCity(request.getParameter("city"));
		formBean.setPostcode(request.getParameter("postcode"));
		formBean.setAdditionalInfo(request.getParameter("aditionalInfo"));
		formBean.setPhone(request.getParameter("phone"));

		// validates captcha
		errors.addAll(captchaService.validateCaptcha(request));

		// validate formBean
		errors.addAll(BeanValidator.validate(formBean));

		// saves new client
		if (errors.isEmpty()) {
			newUser = BeanTransformer.transform(formBean);
			errors.addAll(registrService.saveUser(newUser));
		}

		// forward to registration page with determined errors
		if (!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			request.setAttribute("formBean", formBean);
			if (log.isEnabledFor(Level.DEBUG))
				log.debug("Were found errors in user's info :" + errors);
			request.getRequestDispatcher(
					response.encodeURL(Constants.PAGE_REGISTRATION)).forward(
							request, response);
		} else {
			// redirect to registration page
			response.sendRedirect(response.encodeRedirectURL("registration"));
		}
	}

}
