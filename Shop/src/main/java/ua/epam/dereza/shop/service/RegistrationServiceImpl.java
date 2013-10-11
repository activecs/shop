package ua.epam.dereza.shop.service;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dao.UserDAOImpl;
import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.util.Cryptographer;

/**
 * Default registration's service implementation
 * 
 * @author Eduard_Dereza
 *
 */
public class RegistrationServiceImpl implements RegistrationService {

	private static final Logger log = Logger.getLogger(RegistrationServiceImpl.class);

	private UserDAO  userDAO;

	public RegistrationServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public RegistrationServiceImpl() {
		userDAO = new UserDAOImpl();
	}

	@Override
	public boolean existCurrentUser(String email){
		if(userDAO.findUserByEmail(email) == null)
			return false;

		return true;
	}

	@Override
	public boolean saveUser(UserDTO newUser){
		if(existCurrentUser(newUser.getEmail())){
			String message = "User with given email already exist";
			log.info(message);
			return false;
		}else{
			newUser.setEnabled(true);
			newUser.setPassword(Cryptographer.encode(newUser.getPassword()));
			userDAO.saveUser(newUser);
			log.info("was created new user ->" + newUser);
			return true;
		}
	}
}
