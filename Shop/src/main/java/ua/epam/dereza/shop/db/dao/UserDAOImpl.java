package ua.epam.dereza.shop.db.dao;

import java.util.ArrayList;
import java.util.List;

import ua.epam.dereza.shop.db.dto.UserDTO;

/**
 * UserDAO implementation for collections
 * 
 * @author Eduard_Dereza
 *
 */
public class UserDAOImpl implements UserDAO {

	private List<UserDTO> users;

	public UserDAOImpl() {
		users = new ArrayList<UserDTO>();
	}

	@Override
	public UserDTO findUserByEmail(String email){
		for(UserDTO user : users){
			if(user.getEmail().equals(email))
				return user;
		}

		return null;
	}

	@Override
	public List<UserDTO> findAll(){
		return users;
	}

	@Override
	public void saveUser(UserDTO user){
		synchronized (users) {
			users.add(user);
		}
	}
}
