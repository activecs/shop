package ua.epam.dereza.shop.db.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.Manufacturer;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.ManufacturerDAO;

/**
 * ManufacturerDAO for mysql
 * 
 * @author Eduard_Dereza
 *
 */
public class MysqlManufacturerDAO implements ManufacturerDAO {

	private static final Logger log = Logger.getLogger(MysqlManufacturerDAO.class);

	// ---------------------------------------------
	// SQL queries
	// ---------------------------------------------
	private static final String SQL_FIND_MANUFACTURER_BY_ID = "SELECT * FROM manufacturer WHERE id=?;";
	private static final String SQL_FIND_CATEGORY_BY_NAME = "SELECT * FROM manufacturer WHERE name=?;";
	private static final String SQL_FIND_ALL_MANUFACTURERS = "SELECT * FROM manufacturer;";

	@Override
	public Manufacturer findManufacturerById(Connection conn, int id) throws DAOException {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_MANUFACTURER_BY_ID)){
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			Manufacturer manufacturer = null;
			if (rs.next()) {
				manufacturer = new Manufacturer();
				manufacturer.setId(rs.getInt("id"));
				manufacturer.setName(rs.getString("name"));
			}
			return manufacturer;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public List<Manufacturer> findAllManufacturers(Connection conn) throws DAOException {
		try(Statement stmt = conn.createStatement()){
			List<Manufacturer> list = new ArrayList<>();
			Manufacturer manufacturer = null;
			ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_MANUFACTURERS);
			while (rs.next()) {
				manufacturer = new Manufacturer();
				manufacturer.setId(rs.getInt("id"));
				manufacturer.setName(rs.getString("name"));
				list.add(manufacturer);
			}
			return list;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public Manufacturer findManufacturerByName(Connection conn, String name) throws DAOException {
		try (PreparedStatement pstmt = conn.prepareStatement(SQL_FIND_CATEGORY_BY_NAME)){
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			Manufacturer manufacturer = null;
			if (rs.next()) {
				manufacturer = new Manufacturer();
				manufacturer.setId(rs.getInt(1));
				manufacturer.setName(rs.getString(2));
			}
			return manufacturer;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}
}