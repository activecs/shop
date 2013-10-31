package ua.epam.dereza.shop.db.dao.mysql;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.db.dao.DAOFactory;
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

}
