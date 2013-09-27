package ua.epam.dereza.shop.db.dao;

import java.util.List;

import ua.epam.dereza.shop.db.dto.UserDTO;

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
	 */
	public UserDTO findUserByEmail(String email);

	/**
	 * method for getting all users
	 * 
	 * @return list with all users
	 */
	public List<UserDTO> findAll();

	/**
	 * method for adding new users
	 * 
	 * @param user
	 */
	public void saveUser(UserDTO user);
}
