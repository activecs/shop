package ua.epam.dereza.shop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Order;
import ua.epam.dereza.shop.core.Constants;

/**
 * Servlet implementation class SuccessOrder
 */
@WebServlet("/successOrder")
public class SuccessOrder extends HttpServlet {

	private static final long serialVersionUID = 86280627183079063L;
	private static final Logger log = Logger.getLogger(SuccessOrder.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Order order = (Order) session.getAttribute(Constants.BEAN_ORDER);
		session.removeAttribute(Constants.BEAN_ORDER);

		// send error 400('bad request') if order == null
		if(order == null){
			response.sendError(400);
			return;
		}

		request.setAttribute(Constants.BEAN_ORDER_ID, order.getId());
		request.getRequestDispatcher(Constants.PAGE_SUCCESS_ORDER).forward(request, response);
	}
}
