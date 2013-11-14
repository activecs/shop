package ua.epam.dereza.shop.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.ProductService;

/**
 * Servlet implementation class ProductDetail
 */
@WebServlet("/productDetail")
public class ProductDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(ProductDetail.class);

	static final String FORM_FIELD_PRODUCT_ID = "id";

	private ProductService productService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productService = (ProductService)config.getServletContext().getAttribute(Constants.SERVICE_PRODUCT);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter(FORM_FIELD_PRODUCT_ID);
		try {
			Product product = productService.getProduct(productId);
			if(product == null){
				response.sendError(404);
				return;
			}
			// attach product
			request.setAttribute(Constants.BEAN_PRODUCT, product);
			request.getRequestDispatcher(Constants.PAGE_PRODUCTS_DETAIL).forward(request, response);
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}
}
