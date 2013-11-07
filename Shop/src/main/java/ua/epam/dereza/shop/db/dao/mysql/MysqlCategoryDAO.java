package ua.epam.dereza.shop.db.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Category;
import ua.epam.dereza.shop.db.dao.CategoryDAO;
import ua.epam.dereza.shop.db.dao.DAOException;

/**
 * CategoryDAO for mysql
 * 
 * @author Eduard_Dereza
 *
 */
public class MysqlCategoryDAO implements CategoryDAO {

	private static final Logger log = Logger.getLogger(MysqlCategoryDAO.class);

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_CATEGORY_BY_ID = "SELECT * FROM category WHERE id=?;";
	private static final String SQL_FIND_CATEGORY_BY_NAME = "SELECT * FROM category WHERE name=?;";
	private static final String SQL_FIND_ALL_CATEGORIES = "SELECT * FROM category;";

	@Override
	public Category findCategoryById(Connection conn, int id) throws DAOException {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_CATEGORY_BY_ID)){
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			Category category = null;
			if (rs.next()) {
				category = new Category();
				category.setId(rs.getInt(1));
				category.setName(rs.getString(2));
			}
			return category;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public List<Category> findAllCategories(Connection conn) throws DAOException {
		try (Statement stmt = conn.createStatement()){
			List<Category> list = new ArrayList<>();
			Category category = null;
			ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_CATEGORIES);
			while (rs.next()) {
				category = new Category();
				category.setId(rs.getInt(1));
				category.setName(rs.getString(2));
				list.add(category);
			}
			return list;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public Category findCategoryByName(Connection conn, String name) throws DAOException {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_CATEGORY_BY_NAME)){
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Category category = null;
			if (rs.next()) {
				category = new Category();
				category.setId(rs.getInt(1));
				category.setName(rs.getString(2));
			}
			return category;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}
}
