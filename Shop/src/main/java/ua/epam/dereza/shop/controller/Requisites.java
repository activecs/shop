package ua.epam.dereza.shop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Basket;
import ua.epam.dereza.shop.bean.Order;
import ua.epam.dereza.shop.bean.RequisitesBean;
import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.OrderService;
import ua.epam.dereza.shop.util.BeanValidator;

/**
 * Servlet implementation class Requisites
 */
@WebServlet("/requisites")
public class Requisites extends HttpServlet {

	private static final long serialVersionUID = -2974520623149617831L;
	private static final Logger log = Logger.getLogger(Requisites.class);

	private static final String FORM_FIELD_PAYMENT_METHOD = "paymentMethod";
	private static final String FORM_FIELD_CITY = "city";
	private static final String FORM_FIELD_ADDRESS = "address";
	private static final String FORM_FIELD_PHONE = "phone";

	private OrderService orderService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		orderService = (OrderService) config.getServletContext().getAttribute(Constants.SERVICE_ORDER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		Basket basket = (Basket) session.getAttribute(Constants.BEAN_BASKET);
		if(basket == null || basket.getTotalQuantity().equals(new Integer(0))){
			response.sendError(400);
			return;
		}

		request.setAttribute(Constants.BEAN_ERRORS, session.getAttribute(Constants.BEAN_ERRORS));
		request.setAttribute(Constants.FORM_BEAN, session.getAttribute(Constants.FORM_BEAN));
		session.removeAttribute(Constants.BEAN_ERRORS);
		session.removeAttribute(Constants.FORM_BEAN);

		// sets payment methods
		request.setAttribute(Constants.BEAN_PAYMENT_METHODS, Order.PaymentMethod.values());
		request.getRequestDispatcher(Constants.PAGE_REQUISITES).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		RequisitesBean bean = parseRequest(request);
		List<String> errors = BeanValidator.validate(bean);
		String redirect = null;

		// redirect to /requisites if error's list isn't empty
		if(!errors.isEmpty()){
			session.setAttribute(Constants.BEAN_ERRORS, errors);
			session.setAttribute(Constants.FORM_BEAN, bean);
			redirect = request.getContextPath() + request.getServletPath();
			response.sendRedirect(response.encodeRedirectURL(redirect));
			return;
		}

		Basket basket = (Basket) session.getAttribute(Constants.BEAN_BASKET);
		User user = (User) session.getAttribute(Constants.BEAN_USER);
		Order order = basket.createOrder();
		order.setState(Order.State.GOT);
		order.setDate(new Date());
		order.setUserId(user.getId());
		order.setAddress(bean.getAddress());
		order.setCity(bean.getCity());
		order.setPhone(bean.getPhone());
		order.setMethod(Order.PaymentMethod.valueOf(bean.getPaymentMethod().toUpperCase()));

		try {
			orderService.saveOrder(order);
			log.info("was created new order ->" + order);
		} catch (DAOException e) {
			throw new ServletException(e);
		}
		// removes from session cost and quantity due to basket was cleaned
		session.removeAttribute(Constants.BEAN_BASKET_COST);
		session.removeAttribute(Constants.BEAN_BASKET_QUANTITY);

		session.setAttribute(Constants.BEAN_ORDER, order);

		redirect = getServletContext().getContextPath() + Constants.PATH_SUCCES_ORDER;
		log.trace("redirect to ->" + redirect);
		response.sendRedirect(response.encodeRedirectURL(redirect));
	}

	/**
	 * Parses requisites
	 * 
	 * @param request
	 * @return parsed RequisitesBean
	 */
	private RequisitesBean parseRequest(HttpServletRequest request){
		RequisitesBean bean = new RequisitesBean();
		bean.setCity(request.getParameter(FORM_FIELD_CITY));
		bean.setAddress(request.getParameter(FORM_FIELD_ADDRESS));
		bean.setPhone(request.getParameter(FORM_FIELD_PHONE));
		bean.setPaymentMethod(request.getParameter(FORM_FIELD_PAYMENT_METHOD));

		return bean;
	}
}