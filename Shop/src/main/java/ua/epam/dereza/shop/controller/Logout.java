package ua.epam.dereza.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dto.UserDTO;

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
		UserDTO userDTO = (UserDTO) request.getSession().getAttribute(Constants.USER_BEAN);
		request.getSession().invalidate();
		String redirect = response.encodeRedirectURL(getServletContext().getContextPath());
		if(log.isEnabledFor(Level.DEBUG))
			log.debug("User logouted ->" + userDTO);

		response.sendRedirect(redirect);
	}

}
