package ua.epam.dereza.shop.service;

import ua.epam.dereza.shop.bean.Order;
import ua.epam.dereza.shop.db.dao.DAOException;

public interface OrderService {

	public void saveOrder(Order order) throws DAOException;
}
