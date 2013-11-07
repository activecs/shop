package ua.epam.dereza.shop.db.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Product;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.ProductDAO;
import ua.epam.dereza.shop.service.Query;

/**
 * ProductDAO for mysql
 * 
 * @author Eduard_Dereza
 *
 */
public class MysqlProductDAO implements ProductDAO {

	private static final Logger log = Logger.getLogger(MysqlProductDAO.class);

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_PRODUCT_BY_ID = "SELECT product.id,product.name,price,photo,manufacturer.name as manufacturer,category.name as category,description FROM product INNER JOIN manufacturer ON manufacturer_id=manufacturer.id INNER JOIN category ON category_id=category.id WHERE product.id=?;";
	private static final String SQL_FIND_ALL_PRODUCTS = "SELECT product.id,product.name,price,photo,manufacturer.name as manufacturer,category.name as category,description FROM product INNER JOIN manufacturer ON manufacturer_id=manufacturer.id INNER JOIN category ON category_id=category.id;";
	private static final String SQL_REMOVE_PRODUCT = "DELETE FROM product WHERE name=?;";
	private static final String SQL_UPDATE_PRODUCT = "UPDATE product INNER JOIN manufacturer ON manufacturer.name=? INNER JOIN category ON category.name=? SET product.name=?, price=?, photo=?, description=?, category_id=category.id, manufacturer_id=manufacturer.id WHERE product.id=?;";
	private static final String SQL_CREATE_NEW_PRODUCT = "INSERT INTO product(name,price,photo,description,manufacturer_id,category_id) SELECT ? as name, ? as price, ? as photo, ? as description, manufacturer.id, category.id FROM manufacturer,category WHERE manufacturer.name=? AND category.name=?;";

	@Override
	public Integer findCount(Connection conn, Query query) throws DAOException {
		try (Statement stm = conn.createStatement()){
			ResultSet rs = stm.executeQuery(query.getCountQuery());
			Integer count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			return count;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public List<Product> findProducts(Connection conn, Query query)	throws DAOException {
		try (Statement stm = conn.createStatement()){
			ResultSet rs = stm.executeQuery(query.getProductQuery());
			List<Product> list = new ArrayList<>();
			Product product = null;
			while(rs.next()){
				product = extractProduct(rs);
				list.add(product);
			}
			return list;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public List<Product> findAll(Connection conn) throws DAOException {
		try (Statement stm = conn.createStatement()){
			ResultSet rs = stm.executeQuery(SQL_FIND_ALL_PRODUCTS);
			List<Product> list = new ArrayList<>();
			Product product = null;
			while(rs.next()){
				product = extractProduct(rs);
				list.add(product);
			}
			return list;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public void removeProduct(Connection conn, Product product) throws DAOException {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_REMOVE_PRODUCT);){
			pstmt.setString(1, product.getName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	/**
	 * Extracts product from ResultSet
	 * 
	 * @param rs
	 * @return product
	 * @throws SQLException
	 */
	private Product extractProduct(ResultSet rs) throws SQLException{
		Product product = new Product();
		product.setId(rs.getInt("id"));
		product.setName(rs.getString("name"));
		product.setCategory(rs.getString("category"));
		product.setManufacturer(rs.getString("manufacturer"));
		product.setDescription(rs.getString("description"));
		product.setPhoto(rs.getString("photo"));
		product.setPrice(rs.getBigDecimal("price"));

		return product;
	}

	@Override
	public Product findProduct(Connection conn, String id) throws DAOException {
		try (PreparedStatement pstm = conn.prepareStatement(SQL_FIND_PRODUCT_BY_ID)){
			pstm.setString(1, id);
			ResultSet rs = pstm.executeQuery();
			Product product = null;
			if(rs.next()){
				product = extractProduct(rs);
			}
			return product;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public void updateProduct(Connection conn, Product product) throws DAOException {
		try (PreparedStatement pstm = conn.prepareStatement(SQL_UPDATE_PRODUCT)){
			pstm.setString(1, product.getManufacturer());
			pstm.setString(2, product.getCategory());
			pstm.setString(3, product.getName());
			pstm.setBigDecimal(4, product.getPrice());
			pstm.setString(5, product.getPhoto());
			pstm.setString(6, product.getDescription());
			pstm.setInt(7, product.getId());
			pstm.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public void saveProduct(Connection conn, Product product) throws DAOException {
		try (PreparedStatement pstm = conn.prepareStatement(SQL_CREATE_NEW_PRODUCT)){
			pstm.setString(5, product.getManufacturer());
			pstm.setString(6, product.getCategory());
			pstm.setString(1,product.getName());
			pstm.setBigDecimal(2, product.getPrice());
			pstm.setString(3, product.getPhoto());
			pstm.setString(4, product.getDescription());
			pstm.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}
}