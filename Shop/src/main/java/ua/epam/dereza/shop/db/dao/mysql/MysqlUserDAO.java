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

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.UserDAO;

public class MysqlUserDAO implements UserDAO {

	private static final Logger log = Logger.getLogger(MysqlUserDAO.class);

	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT id,email,password,avatar,firstName,lastName,birthDate,company,address1,address2,city,postcode,additionalInfo,phone,role,enabled,loginAttemptCount,lastSuccessLogin,nextUnban FROM user WHERE email=?;";
	private static final String SQL_FIND_ALL_USERS = "SELECT users.id,email,password,avatar,firstName,lastName,birthDate,company,address1,address2,city,postcode,additionalInfo,phone,role,enabled,loginAttemptCount,lastSuccessLogin,nextUnban FROM user;";
	private static final String SQL_UPDATE_USER_INFO = "UPDATE user SET role=?, password=?, avatar=?,firstName=?,lastName=?,birthDate=?,company=?,address1=?,address2=?,city=?,postcode=?,additionalInfo=?,phone=?,enabled=?,loginAttemptCount=?,lastSuccessLogin=?,nextUnban=? WHERE email=?;";
	private static final String SQL_CREATE_NEW_USER = "INSERT INTO user(role,password,avatar,firstName,lastName,birthDate,company,address1,address2,city,postcode,additionalInfo,phone,email)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	private static final String SQL_REMOVE_USER_WITH_GIVEN_EMAIL = "DELETE FROM user WHERE email=?";

	@Override
	public User findUserByEmail(Connection conn, String email)
			throws DAOException {
		try {
			User user = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = conn.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();
			if (rs.next())
				user = extractUserDTO(rs);
			return user;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public List<User> findAll(Connection conn) throws DAOException {
		try {
			List<User> users = new ArrayList<>();
			User user = null;
			Statement stmt = null;
			ResultSet rs = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_FIND_ALL_USERS);
			while (rs.next()) {
				user = extractUserDTO(rs);
				users.add(user);
			}
			return users;
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public void saveUser(Connection conn, User user) throws DAOException {
		try {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(SQL_CREATE_NEW_USER);
			packUserDTO(pstmt, user);
			pstmt.setString(14, user.getEmail());
			pstmt.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}

	}

	@Override
	public void updateUser(Connection conn, User user) throws DAOException {
		try {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(SQL_UPDATE_USER_INFO);
			packUserDTO(pstmt, user);
			pstmt.setBoolean(14, user.getEnabled());
			pstmt.setInt(15, user.getLoginAttemptCount());
			Timestamp lastSuccessLogin = user.getLastSuccessLogin() == null ? null : new Timestamp(user.getLastSuccessLogin().getTime());
			pstmt.setTimestamp(16, lastSuccessLogin);
			Timestamp nextUnban = user.getNextUnban() == null ? null : new Timestamp(user.getNextUnban().getTime());
			pstmt.setTimestamp(17, nextUnban);
			pstmt.setString(18, user.getEmail());
			pstmt.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	@Override
	public void removeUser(Connection conn, String email) throws DAOException {
		try {
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(SQL_REMOVE_USER_WITH_GIVEN_EMAIL);
			pstmt.setString(1, email);
			pstmt.executeUpdate();
		} catch (Exception e) {
			String message = "Cannot perform query";
			log.error(message, e);
			throw new DAOException(message, e);
		}
	}

	private User extractUserDTO(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setAvatar(rs.getString("avatar"));
		user.setFirstName(rs.getString("firstName"));
		user.setLastName(rs.getString("lastName"));
		user.setBirthDate(rs.getTimestamp("birthDate"));
		user.setCompany(rs.getString("company"));
		user.setAddress1(rs.getString("address1"));
		user.setAddress2(rs.getString("address2"));
		user.setCity(rs.getString("city"));
		user.setPostCode(rs.getInt("postcode"));
		user.setAdditionalInfo(rs.getString("additionalInfo"));
		user.setPhone(rs.getString("phone"));
		user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase()));
		user.setEnabled(rs.getBoolean("enabled"));
		user.setLoginAttemptCount(rs.getInt("loginAttemptCount"));
		user.setLastSuccessLogin(rs.getTimestamp("lastSuccessLogin"));
		user.setNextUnban(rs.getTimestamp("nextUnban"));
		return user;
	}

	private PreparedStatement packUserDTO(PreparedStatement pstmt, User user)
			throws SQLException {
		pstmt.setString(1, user.getRole().toString());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getAvatar());
		pstmt.setString(4, user.getFirstName());
		pstmt.setString(5, user.getLastName());
		pstmt.setTimestamp(6, new Timestamp(user.getBirthDate().getTime()));
		pstmt.setString(7, user.getCompany());
		pstmt.setString(8, user.getAddress1());
		pstmt.setString(9, user.getAddress2());
		pstmt.setString(10, user.getCity());
		pstmt.setInt(11, user.getPostCode());
		pstmt.setString(12, user.getAdditionalInfo());
		pstmt.setString(13, user.getPhone());
		return pstmt;
	}
}
