package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;

import org.apache.log4j.Logger;

/**
 * Manages transactions(gets, commits, rollbacks, closes)
 * 
 * @author Eduard_Dereza
 *
 */
public class TransactionManagerImpl implements TransactionManager {

	private static final Logger log = Logger.getLogger(TransactionManagerImpl.class);

	@Override
	public Object doInTransaction(TransactionOperation operation) throws DAOException{
		Object res = null;
		Connection conn = null;
		try{
			conn = ConnectionManager.getConnection();
			res = operation.execute(conn);
			commit(conn);
		}catch(DAOException e){
			rollback(conn);
			throw e;
		}finally{
			close(conn);
		}

		return res;
	}

	private void rollback(Connection conn) throws DAOException{
		try {
			if (conn != null)
				conn.rollback();
		} catch (Exception e) {
			String message = "Cannot rollback";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	private void commit(Connection conn) throws DAOException{
		try {
			conn.commit();
		} catch (Exception e) {
			String message = "Cannot commit connection";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	private void close(Connection conn) throws DAOException{
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			String message = "Cannot close connection";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}
}
