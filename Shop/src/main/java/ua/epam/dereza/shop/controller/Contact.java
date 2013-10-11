package ua.epam.dereza.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.core.Constants;

/**
 * Controller for feedback page
 * 
 * @author Eduard_Dereza
 *
 */
@WebServlet("/contact")
public class Contact extends HttpServlet {

	private static final long serialVersionUID = 98466714269630098L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// forward to contacts
		request.getRequestDispatcher(Constants.PAGE_CONTACT).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO: email from user to admin
	}

}
