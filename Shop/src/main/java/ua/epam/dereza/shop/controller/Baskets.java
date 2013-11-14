package ua.epam.dereza.shop.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ua.epam.dereza.shop.bean.Basket;
import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.ProductService;

/**
 * Servlet implementation class Basket
 */
@WebServlet("/basket")
public class Baskets extends HttpServlet {

	private static final long serialVersionUID = -1383914354962786022L;
	private static final Logger log = Logger.getLogger(Baskets.class);

	public enum Actions {
		REDUCE, ADD, REMOVE
	}

	private ProductService productService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productService = (ProductService) config.getServletContext().getAttribute(Constants.SERVICE_PRODUCT);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// forward to basket
		request.getRequestDispatcher(Constants.PAGE_BASKET).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> resp = new HashMap<>();
		HttpSession session = request.getSession();
		Basket basket = (Basket) session.getAttribute(Constants.BEAN_BASKET);
		if (basket == null)
			basket = new Basket();

		String action = request.getParameter("action");
		String productId = request.getParameter("productId");
		Product product = null;
		try {
			if (action != null) {
				log.trace("path info ->" + action);
				Actions act = Actions.valueOf(action.toUpperCase());
				switch (act) {
				case ADD:
					product = productService.getProduct(productId);
					if(product != null){
						Integer qnt = null;
						try{
							qnt = new Integer(request.getParameter("quantity"));
						}catch(Exception e){
							qnt = 1;
						}
						basket.addProduct(product, qnt);
					}
					break;
				case REMOVE:
					product = productService.getProduct(productId);
					if(product != null)
						basket.removeProduct(product);
					break;
				case REDUCE:
					product = productService.getProduct(productId);
					if(product != null)
						basket.reduceProduct(product);
					break;
				}
			}

			session.setAttribute(Constants.BEAN_BASKET_COST, basket.getTotalCost());
			session.setAttribute(Constants.BEAN_BASKET_QUANTITY, basket.getTotalQuantity());
			session.setAttribute(Constants.BEAN_BASKET, basket);

			resp.put(Constants.BEAN_BASKET_CONCRETE_PRODUCT_PRICE, (product != null ? product.getPrice() : 0));
			resp.put(Constants.BEAN_BASKET_CONCRETE_PRODUCT_QUANTITY, basket.getProductQuantity(product));

			resp.put(Constants.BEAN_BASKET_COST, basket.getTotalCost());
			resp.put(Constants.BEAN_BASKET_QUANTITY, basket.getTotalQuantity());
			response.setContentType("text/json");
			response.getWriter().write(new Gson().toJson(resp));
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}
}