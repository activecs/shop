package ua.epam.dereza.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.core.Constants;

/**
 * Controller for managing user's account
 * 
 * @author Eduard_Dereza
 *
 */
@WebServlet("/account")
public class Account extends HttpServlet {

	private static final long serialVersionUID = -4404723739277656594L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// forwards to user's account page
		request.getRequestDispatcher(Constants.PAGE_ACCOUNT).forward(request, response);
	}


}
