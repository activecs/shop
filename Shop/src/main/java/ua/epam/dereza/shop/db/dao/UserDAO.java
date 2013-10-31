package ua.epam.dereza.shop.db.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ua.epam.dereza.shop.bean.User;

/**
 * Basic data access object for user
 * 
 * @author Eduard_Dereza
 * 
 */
public interface UserDAO {

	/**
	 * method for getting user by email
	 * 
	 * @param email
	 * @return user with current email
	 * @throws SQLException
	 * @throws DAOException
	 */
	public User findUserByEmail(Connection conn, String email) throws DAOException;

	/**
	 * method for getting all users
	 * 
	 * @throws DAOException
	 * @return list with all users
	 */
	public List<User> findAll(Connection conn) throws DAOException;

	/**
	 * method for adding new users
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void saveUser(Connection conn, User user) throws DAOException;

	/**
	 * method for updating info about user
	 *
	 * @param user
	 * @throws DAOException
	 */
	public void updateUser(Connection conn, User user) throws DAOException;

	/**
	 * removes user from db with given email
	 * 
	 * @param conn
	 * @param email
	 * @throws DAOException
	 */
	public void removeUser(Connection conn, String email) throws DAOException;
}
