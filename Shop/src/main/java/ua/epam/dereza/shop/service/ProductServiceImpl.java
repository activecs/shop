package ua.epam.dereza.shop.service;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Category;
import ua.epam.dereza.shop.bean.Manufacturer;
import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.db.dao.CategoryDAO;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.ManufacturerDAO;
import ua.epam.dereza.shop.db.dao.ProductDAO;
import ua.epam.dereza.shop.db.dao.TransactionManager;
import ua.epam.dereza.shop.db.dao.TransactionManagerImpl;
import ua.epam.dereza.shop.db.dao.TransactionOperation;

public class ProductServiceImpl implements ProductService {

	private static final Logger log = Logger.getLogger(ProductServiceImpl.class);
	private ProductDAO productDAO;
	private ManufacturerDAO manufacturerDAO;
	private CategoryDAO categoryDAO;
	private TransactionManager transManager;

	public ProductServiceImpl(DAOFactory daoFactory) {
		productDAO = daoFactory.getProductDAO();
		manufacturerDAO = daoFactory.getManufacturerDAO();
		categoryDAO = daoFactory.getCategoryDAO();
		transManager = new TransactionManagerImpl();
	}

	@Override
	public int getAmount(final Query query) throws DAOException {
		return (Integer) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {
				if (log.isTraceEnabled())
					log.trace("Prepared query -> " + query.getCountQuery());

				Integer count = productDAO.findCount(conn, query);

				if (log.isTraceEnabled())
					log.trace("found goods in db -> " + count);

				return count;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProducts(final Query query) throws DAOException {
		return (List<Product>) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(final Connection conn) throws DAOException {
				if (log.isTraceEnabled())
					log.trace("Prepared query -> " + query.getProductQuery());

				List<Product> products = productDAO.findProducts(conn, query);

				if (log.isTraceEnabled())
					log.trace("found goods in db -> " + products);

				return products;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Manufacturer> getManufacturers() throws DAOException {
		return (List<Manufacturer>) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(final Connection conn) throws DAOException {
				List<Manufacturer> manufacturers = null;
				manufacturers = manufacturerDAO.findAllManufacturers(conn);

				if (log.isTraceEnabled())
					log.trace("found manufacturers in db -> " + manufacturers);

				return manufacturers;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getCategories() throws DAOException {
		return (List<Category>) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(final Connection conn) throws DAOException {
				List<Category> categories = categoryDAO.findAllCategories(conn);

				if (log.isTraceEnabled())
					log.trace("found categories in db -> " + categories);

				return categories;
			}
		});
	}

	@Override
	public Product getProduct(final String id) throws DAOException {
		return (Product) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(final Connection conn) throws DAOException {
				Product product  = productDAO.findProduct(conn, id);
				if (log.isTraceEnabled())
					log.trace("found product in db -> " + product);

				return product;
			}
		});
	}
}