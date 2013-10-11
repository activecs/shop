package ua.epam.dereza.shop.service;

import ua.epam.dereza.shop.db.dto.UserDTO;

/**
 * Service for registration new users
 * 
 * @author Eduard_Dereza
 *
 */
public interface RegistrationService {

	/**
	 * Checks whether current user exists
	 * 
	 * @param email
	 * @return true if current user exists
	 */
	public boolean existCurrentUser(String email);

	/**
	 * Adds new user
	 * 
	 * @param newUser
	 * @return true if user was created
	 */
	public boolean saveUser(UserDTO newUser);
}
