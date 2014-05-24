package ua.epam.dereza.shop.service;

import java.sql.Connection;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Order;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.OrderDAO;
import ua.epam.dereza.shop.db.dao.TransactionManager;
import ua.epam.dereza.shop.db.dao.TransactionManagerImpl;
import ua.epam.dereza.shop.db.dao.TransactionOperation;

public class OrderServiceImpl implements OrderService{

	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
	private TransactionManager transManager;
	private OrderDAO orderDAO;

	public OrderServiceImpl(DAOFactory daoFactory) {
		orderDAO = daoFactory.getOrderDAO();
		transManager = new TransactionManagerImpl();
	}

	@Override
	public void saveOrder(final Order order) throws DAOException {
		transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {
				orderDAO.insertOrder(conn, order);
				log.trace("Was saved order ->" + order);
				return null;
			}
		});
	}
}
