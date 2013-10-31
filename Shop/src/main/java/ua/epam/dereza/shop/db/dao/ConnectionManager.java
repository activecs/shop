package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Manages pool of connections
 * 
 * @author Eduard_Dereza
 *
 */
public class ConnectionManager {

	private static final Logger log = Logger.getLogger(ConnectionManager.class);
	private static Context initContext;
	private static Context envContext;
	private static DataSource ds;

	private static void initContext() throws NamingException{
		try {
			initContext = new InitialContext();
			envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) (envContext.lookup("jdbc/derezaShop"));
		} catch (NamingException ex) {
			log.error("Cannot init pool of connection", ex);
			throw ex;
		}
	}

	public static synchronized Connection getConnection() throws DAOException{
		try{
			if(ds == null)
				initContext();
			Connection connection = ds.getConnection();
			return connection;
		}catch(Exception e){
			log.error("Cannot create connection", e);
			throw new DAOException(e);
		}
	}
}
