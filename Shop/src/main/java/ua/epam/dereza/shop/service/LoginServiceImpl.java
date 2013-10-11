package ua.epam.dereza.shop.service;

import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.util.Cryptographer;

/**
 * LoginService implementation
 * 
 * @author Eduard_Dereza
 *
 */
public class LoginServiceImpl implements LoginService {

	private UserDAO userDAO;

	public LoginServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public boolean authentificate(String email, String password) {
		if(email == null || email.length() == 0 || password == null){
			return false;
		}

		UserDTO userDTO = userDAO.findUserByEmail(email);
		if(userDTO == null){
			return false;
		}

		String encodedPassword = userDTO.getPassword();
		if(!Cryptographer.encode(password).equals(encodedPassword)){
			return false;
		}

		return true;
	}

	@Override
	public UserDTO signIn(String email, String password) {
		if(!authentificate(email, password))
			throw new IllegalArgumentException("Illegal email or password");

		UserDTO userDTO = userDAO.findUserByEmail(email);

		return userDTO;
	}

}
