package ua.epam.dereza.shop.db.dao;

/**
 * Manages transactions
 * 
 * @author Eduard_Dereza
 *
 */
public interface TransactionManager {

	public Object doInTransaction(TransactionOperation operation) throws DAOException;
}
