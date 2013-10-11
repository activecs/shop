package ua.epam.dereza.shop.service;

import ua.epam.dereza.shop.db.dto.UserDTO;

/**
 * Service for logging in
 * 
 * @author Eduard_Dereza
 *
 */
public interface LoginService {

	/**
	 * Checks whether current user exists and checks his password
	 * 
	 * @param email
	 * @param password
	 * @return if authentificated
	 */
	public boolean authentificate(String email, String password);

	/**
	 * Performs authentication on the site
	 * 
	 * @param email
	 * @param password
	 * @return userDTO
	 */
	public UserDTO signIn(String email, String password);
}
