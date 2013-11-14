package ua.epam.dereza.shop.db.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Order;
import ua.epam.dereza.shop.bean.OrderItem;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.OrderDAO;

public class MysqlOrderDAO implements OrderDAO {

	private static final Logger log = Logger.getLogger(MysqlOrderDAO.class);

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_CREATE_NEW_ORDER = "INSERT INTO order_info(user_id,state,details,method,city,address,phone,date) VALUES(?,?,?,?,?,?,?,?);";
	private static final String SQL_CREATE_NEW_ORDER_ITEM = "INSERT INTO order_item(order_info_id,product_id,quantity,price) VALUES(?,?,?,?);";
	private static final String SQL_FIND_ORDER_BY_ID = "SELECT * FROM order_info WHERE id=?;";
	private static final String SQL_FIND_ORDER_BY_USER_ID = "SELECT * FROM order_info WHERE user_id=?;";
	private static final String SQL_FIND_ORDER_ITEM = "SELECT * FROM order_info WHERE order_info_id=?;";
	private static final String SQL_UPDATE_ORDER = "UPDATE order_info SET user_id=?, state=?, details=?, method=?, city=?, address=?, phone=?, date=?;";

	@Override
	public void insertOrder(Connection conn, Order order) throws DAOException {
		try (PreparedStatement orderInfo = conn.prepareStatement(SQL_CREATE_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement orderItem = conn.prepareStatement(SQL_CREATE_NEW_ORDER_ITEM);){
			packOrderInfo(orderInfo, order);
			orderInfo.executeUpdate();
			ResultSet key = orderInfo.getGeneratedKeys();
			key.next();
			order.setId(key.getInt(1));

			for(OrderItem item : order.getItems()){
				orderItem.setInt(1, order.getId());
				orderItem.setInt(2, item.getProductId());
				orderItem.setInt(3, item.getQuantity());
				orderItem.setBigDecimal(4, item.getPrice());
				orderItem.executeUpdate();
			}
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public void updateOrder(Connection conn, Order order) throws DAOException {
		try (PreparedStatement orderInfo = conn.prepareStatement(SQL_UPDATE_ORDER)){
			packOrderInfo(orderInfo, order);
			orderInfo.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public Order findOrderById(Connection conn, String id) throws DAOException {
		try (PreparedStatement orderInfo = conn.prepareStatement(SQL_FIND_ORDER_BY_ID)){
			ResultSet rs = null;
			Order order = null;
			List<OrderItem> items = findOrderItemsByOrderId(conn, id);
			order = new Order(items);
			orderInfo.setString(1, id);
			rs = orderInfo.executeQuery();
			if(rs.next())
				extractOrderInfo(rs, order);
			return order;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	private List<OrderItem> findOrderItemsByOrderId(Connection conn, String id) throws DAOException{
		try (PreparedStatement orderItem = conn.prepareStatement(SQL_FIND_ORDER_ITEM)){
			ResultSet rs = null;
			OrderItem item = null;
			List<OrderItem> items = new ArrayList<>();
			orderItem.setString(1, id);
			rs = orderItem.executeQuery();
			while(rs.next()){
				item = new OrderItem(rs.getInt("product_id"), rs.getInt("quantity"), rs.getBigDecimal("price"));
				items.add(item);
			}
			return items;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public List<Order> findOrdersByUserId(Connection conn, String userId) throws DAOException {
		try (PreparedStatement orderInfo = conn.prepareStatement(SQL_FIND_ORDER_BY_USER_ID)){
			List<Order> orders = new ArrayList<>();
			orderInfo.setString(1, userId);
			ResultSet rs = orderInfo.executeQuery();
			Order order = null;
			List<OrderItem> items = null;
			while(rs.next()){
				order = new Order();
				extractOrderInfo(rs, order);
				items = findOrderItemsByOrderId(conn, order.getId().toString());
				order.setItems(items);
				orders.add(order);
			}
			return orders;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	private void packOrderInfo(PreparedStatement orderInfo, Order order) throws SQLException {
		orderInfo.setInt(1, order.getUserId());
		orderInfo.setString(2, order.getState().name());
		orderInfo.setString(3, order.getDetails());
		orderInfo.setString(4, order.getMethod().name());
		orderInfo.setString(5, order.getCity());
		orderInfo.setString(6, order.getAddress());
		orderInfo.setString(7, order.getPhone());
		orderInfo.setTimestamp(8, new Timestamp(order.getDate().getTime()));
	}

	private void extractOrderInfo(ResultSet rs, Order order) throws SQLException{
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setState(Order.State.valueOf(rs.getString("state").toUpperCase()));
		order.setDetails(rs.getString("details"));
		order.setMethod(Order.PaymentMethod.valueOf(rs.getString("method").toUpperCase()));
		order.setCity(rs.getString("city"));
		order.setAddress(rs.getString("address"));
		order.setPhone(rs.getString("phone"));
		order.setDate(rs.getTimestamp("date"));
	}
}