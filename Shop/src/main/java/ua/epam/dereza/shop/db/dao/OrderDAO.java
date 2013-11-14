package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;
import java.util.List;

import ua.epam.dereza.shop.bean.Order;

public interface OrderDAO {

	public void insertOrder(Connection conn, Order order) throws DAOException;

	public void updateOrder(Connection conn, Order order) throws DAOException;

	public Order findOrderById(Connection conn, String id) throws DAOException;

	public List<Order> findOrdersByUserId(Connection conn, String userId) throws DAOException;
}
