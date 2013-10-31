package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;

/**
 * DAO operation's container
 * 
 * @author Eduard_Dereza
 *
 */
public interface TransactionOperation {

	public Object execute(Connection conn) throws DAOException;

}
