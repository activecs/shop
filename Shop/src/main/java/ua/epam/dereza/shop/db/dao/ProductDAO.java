package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;
import java.util.List;

import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.service.Query;

/**
 * DAO for products
 * 
 * @author Eduard_Dereza
 *
 */
public interface ProductDAO {

	public Integer findCount(Connection conn, Query query) throws DAOException;

	public List<Product> findProducts(Connection conn, Query query) throws DAOException;

	public Product findProduct(Connection conn, String id) throws DAOException;

	public List<Product> findAll(Connection conn) throws DAOException;

	public void removeProduct(Connection conn, Product product) throws DAOException;

	public void updateProduct(Connection conn, Product product) throws DAOException;

	public void saveProduct(Connection conn, Product product) throws DAOException;
}
