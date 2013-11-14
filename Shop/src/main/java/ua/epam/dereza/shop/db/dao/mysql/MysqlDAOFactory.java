package ua.epam.dereza.shop.db.dao.mysql;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.db.dao.CategoryDAO;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.ManufacturerDAO;
import ua.epam.dereza.shop.db.dao.OrderDAO;
import ua.epam.dereza.shop.db.dao.ProductDAO;
import ua.epam.dereza.shop.db.dao.UserDAO;

/**
 * DAO factory for mysql database
 * 
 * @author Eduard_Dereza
 *
 */
public class MysqlDAOFactory implements DAOFactory{

	private static final Logger log = Logger.getLogger(MysqlDAOFactory.class);

	@Override
	public UserDAO getUserDAO() {
		return new MysqlUserDAO();
	}

	@Override
	public ProductDAO getProductDAO() {
		return new MysqlProductDAO();
	}

	@Override
	public ManufacturerDAO getManufacturerDAO() {
		return new MysqlManufacturerDAO();
	}

	@Override
	public CategoryDAO getCategoryDAO() {
		return new MysqlCategoryDAO();
	}

	@Override
	public OrderDAO getOrderDAO() {
		return new MysqlOrderDAO();
	}

}
