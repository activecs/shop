package ua.epam.dereza.shop.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Category;
import ua.epam.dereza.shop.bean.Manufacturer;
import ua.epam.dereza.shop.bean.ProductQueryParam;
import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.ProductService;
import ua.epam.dereza.shop.service.Query;

/**
 * Product's view controller
 * 
 * @author Eduard_Dereza
 *
 */
@WebServlet("/products")
public class Products extends HttpServlet {

	private static final long serialVersionUID = -4901757933063770583L;
	private static final Logger log = Logger.getLogger(Products.class);

	// form's fields
	static final String FORM_FIELD_CATEGORY = "category";
	static final String FORM_FIELD_SORT_BY = "sortBy";
	static final String FORM_FIELD_MANUFACTURERS = "manufacturers";
	static final String FORM_FIELD_PRICE_FROM = "priceFrom";
	static final String FORM_FIELD_PRICE_TO = "priceTo";
	static final String FORM_FIELD_KEYWORD = "keyword";
	static final String FORM_FIELD_ITEM_PER_PAGE = "itemPerPage";
	static final String FORM_FIELD_PAGE = "page";

	static final Integer ITEM_PER_PAGE_DEFAULT = 6;
	static final Integer ITEM_PER_PAGE_MIN_VALUE = 5;
	static final Integer ITEM_PER_PAGE_MAX_VALUE = 50;

	private static final Integer PAGE_DEFAULT = 1;
	private ProductService productService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		productService = (ProductService)config.getServletContext().getAttribute(Constants.SERVICE_PRODUCT);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductQueryParam filter = new ProductQueryParam();

		// parses and validates form
		parseRequest(request, filter);
		if(log.isTraceEnabled())
			log.trace("Validated form ->" + filter);

		// gets products
		Integer productAmount = null;
		List<Product> products;
		List<Manufacturer> manufacturers;
		List<Category> categories;
		Integer pagesAmount = null;
		try{
			Query.Builder builder = new Query.Builder()
			.category(filter.getCategory())
			.keyword(filter.getKeyword())
			.manufacturers(filter.getManufacturers())
			.priceFrom(filter.getPriceFrom())
			.priceTo(filter.getPriceTo())
			.sortBy(filter.getSortBy());
			// amount query
			Query amountQuery = builder.build();
			productAmount = productService.getAmount(amountQuery);

			// product query
			Integer offset = calculateOffset(productAmount, filter);
			Integer productPerPage = Integer.valueOf(filter.getItemPerPage());
			pagesAmount = productAmount/productPerPage + 1;
			Query productsQuery = builder.limit(productPerPage).offset(offset).build();
			products = productService.getProducts(productsQuery);

			manufacturers = productService.getManufacturers();
			categories = productService.getCategories();
		}catch (DAOException e) {
			throw new ServletException(e);
		}

		// attach formBean and list of products
		request.setAttribute(Constants.FORM_BEAN, filter);
		request.setAttribute(Constants.BEAN_PRODUCTS, products);
		request.setAttribute(Constants.BEAN_MANUFACTURERS, manufacturers);
		request.setAttribute(Constants.BEAN_CATEGORIES, categories);
		request.setAttribute(Constants.BEAN_PAGE_AMOUNT, pagesAmount);

		// forward to list of products
		request.getRequestDispatcher(Constants.PAGE_PRODUCTS).forward(request, response);
	}

	/**
	 * Calculates product's offset for query builder
	 * 
	 * @param totalProductCount
	 * @param formBean
	 * @return calculated offset
	 */
	private Integer calculateOffset(Integer totalProductCount, ProductQueryParam filter){
		Integer productPerPage = filter.getItemPerPage();
		Integer page = filter.getPage();

		// last page validation
		Integer pagesCount = totalProductCount/productPerPage + 1;
		if(page > pagesCount){
			page = PAGE_DEFAULT;
			filter.setPage(page);
		}
		Integer offset = (page - 1) * productPerPage;
		return offset;
	}

	/**
	 * Performs basic request validation and create filter
	 * 
	 * @param req
	 * @param filter
	 */
	private void parseRequest(HttpServletRequest req, ProductQueryParam filter){
		// category
		String category = req.getParameter(FORM_FIELD_CATEGORY);
		if(category != null && category.trim().length() != 0)
			filter.setCategory(category);

		// sortBy
		String sortBy = req.getParameter(FORM_FIELD_SORT_BY);
		if(sortBy != null && sortBy.trim().length() != 0)
			filter.setSortBy(sortBy);

		// manufacturer
		String manufacturers[] = req.getParameterValues(FORM_FIELD_MANUFACTURERS);
		if(manufacturers != null){
			List<String> manuf = new ArrayList<>();
			for (String manufacturer : manufacturers)
				if(manufacturer.trim().length() != 0)
					manuf.add(manufacturer.trim());
			filter.setManufacturers(manuf);
		}

		// priceFrom and priceTo
		try{
			Double priceFrom = new Double(req.getParameter(FORM_FIELD_PRICE_FROM));
			Double priceTo = new Double(req.getParameter(FORM_FIELD_PRICE_TO));
			if(priceFrom < priceTo && priceFrom > 0){
				filter.setPriceFrom(new BigDecimal(priceFrom));
				filter.setPriceTo(new BigDecimal(priceTo));
			}
		}catch(Exception ex){
			filter.setPriceFrom(null);
			filter.setPriceTo(null);
		}
		// keyword
		String keyword = req.getParameter(FORM_FIELD_KEYWORD);
		if(keyword != null && keyword.trim().length() != 0)
			filter.setKeyword(keyword);

		// page
		try{
			Integer page = Integer.valueOf(req.getParameter(FORM_FIELD_PAGE));
			if(page < 0)
				filter.setPage(PAGE_DEFAULT);
			else
				filter.setPage(page);
		}catch(Exception e){
			filter.setPage(PAGE_DEFAULT);
		}
		// itemPerPage
		try{
			Integer itemPerPagePage = Integer.valueOf(req.getParameter(FORM_FIELD_ITEM_PER_PAGE));
			if(itemPerPagePage < ITEM_PER_PAGE_MIN_VALUE || itemPerPagePage > ITEM_PER_PAGE_MAX_VALUE)
				filter.setItemPerPage(ITEM_PER_PAGE_DEFAULT);
			else
				filter.setItemPerPage(itemPerPagePage);
		}catch(Exception ex){
			filter.setItemPerPage(ITEM_PER_PAGE_DEFAULT);
		}
	}
}