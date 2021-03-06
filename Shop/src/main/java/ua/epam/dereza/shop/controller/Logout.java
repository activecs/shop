package ua.epam.dereza.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;

/**
 * Log out controller
 * 
 * @author Eduard_Dereza
 *
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {

	private static final long serialVersionUID = -1355577513422246711L;
	private static final Logger log = Logger.getLogger(Logout.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userDTO = (User) request.getSession().getAttribute(Constants.BEAN_USER);
		request.getSession().invalidate();
		String redirect = response.encodeRedirectURL(getServletContext().getContextPath());
		if(log.isEnabledFor(Level.DEBUG))
			log.debug("User logouted ->" + userDTO);

		response.sendRedirect(redirect);
	}

}
