package ua.epam.dereza.shop.service;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.db.dao.DAOException;

public interface UserService {

	// authentification possible states
	public static enum AuthState {
		BANNED, INCORRECT, OK
	}

	/**
	 * Checks whether current user exists and checks his password
	 * 
	 * @param email
	 * @param password
	 * @return enum of state
	 * @throws DAOException
	 */
	public AuthState authentificate(String email, String password) throws DAOException;

	/**
	 * Checks whether current user exists
	 * 
	 * @param email
	 * @return true if current user exists
	 * @throws DAOException
	 */
	public boolean isExistUser(String email) throws DAOException;

	/**
	 * Adds new user
	 * 
	 * @param newUser
	 * @return true if user was created
	 * @throws DAOException
	 */
	public boolean addUser(User newUser) throws DAOException;

	/**
	 * Return user with given email
	 * 
	 * @param email
	 * @return user if he exists or null he doesn't exist
	 * @throws DAOException
	 */
	public User findUserByEmail(String email) throws DAOException;

	/**
	 * Removes user with given email
	 * 
	 * @param email
	 * @throws DAOException
	 */
	public void removeUser(String email) throws DAOException;
}
