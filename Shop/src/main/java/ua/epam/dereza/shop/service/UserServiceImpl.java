package ua.epam.dereza.shop.service;

import java.sql.Connection;
import java.util.Date;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.TransactionManager;
import ua.epam.dereza.shop.db.dao.TransactionManagerImpl;
import ua.epam.dereza.shop.db.dao.TransactionOperation;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.util.Cryptographer;

public class UserServiceImpl implements UserService {

	private final Integer MAX_UNSUCCESSFUL_ATTEMPTS = 5;
	private final long BAN_PERIOD = 1000 * 60 * 30; // 30 minutes
	private UserDAO userDAO;
	private TransactionManager transManager;
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	public UserServiceImpl(DAOFactory daoFactory) {
		userDAO = daoFactory.getUserDAO();
		transManager = new TransactionManagerImpl();
	}

	@Override
	public AuthState authentificate(final String email, final String password) throws DAOException {
		return (AuthState) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {

				if (email == null || email.length() == 0 || password == null)
					return AuthState.INCORRECT;

				User userDTO = userDAO.findUserByEmail(conn, email);
				if(log.isTraceEnabled())
					log.trace("was found user in db ->" + userDTO);
				if (userDTO == null)
					return AuthState.INCORRECT;

				Date nextUnban = userDTO.getNextUnban();
				if (nextUnban != null && nextUnban.before(new Date()))
					unBanUser(conn, userDTO);

				if (!userDTO.getEnabled())
					return AuthState.BANNED;

				String encodedPassword = userDTO.getPassword();
				if (!Cryptographer.encode(password).equals(encodedPassword)) {
					int loginAttempts = userDTO.getLoginAttemptCount();
					loginAttempts++;
					userDTO.setLoginAttemptCount(loginAttempts);
					if (loginAttempts >= MAX_UNSUCCESSFUL_ATTEMPTS){
						banUser(conn, userDTO);
						return AuthState.BANNED;
					}else{
						userDAO.updateUser(conn, userDTO);
						return AuthState.INCORRECT;
					}
				}

				// if evething is ok
				userDTO.setLastSuccessLogin(new Date());
				userDTO.setLoginAttemptCount(0);
				userDAO.updateUser(conn, userDTO);
				return AuthState.OK;
			}
		});
	}

	@Override
	public boolean isExistUser(final String email) throws DAOException {
		return (Boolean) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {
				if (userDAO.findUserByEmail(conn, email) == null)
					return false;
				return true;
			}
		});
	}

	@Override
	public boolean addUser(final User newUser) throws DAOException {
		if (isExistUser(newUser.getEmail())) {
			String message = "User with given email already exist";
			log.info(message);
			return false;
		}
		return (Boolean) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {
				newUser.setEnabled(true);
				newUser.setPassword(Cryptographer.encode(newUser.getPassword()));
				userDAO.saveUser(conn, newUser);
				log.info("was created new user ->" + userDAO.findUserByEmail(conn, newUser.getEmail()));
				return true;
			}
		});
	}

	private void banUser(Connection conn, final User userDTO) throws DAOException {
		userDTO.setEnabled(false);
		Date now = new Date();
		Date willBeUnban = new Date(now.getTime() + BAN_PERIOD);
		userDTO.setNextUnban(willBeUnban);
		userDTO.setLoginAttemptCount(0);
		userDAO.updateUser(conn, userDTO);
	}

	private void unBanUser(Connection conn, final User userDTO) throws DAOException {
		userDTO.setEnabled(true);
		userDTO.setNextUnban(null);
		userDAO.updateUser(conn, userDTO);
	}

	@Override
	public User findUserByEmail(final String email) throws DAOException {
		return (User) transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {
				User userDTO = userDAO.findUserByEmail(conn, email);

				if(log.isDebugEnabled())
					log.trace("Found user in db ->" + userDTO);

				return userDTO;
			}
		});
	}

	@Override
	public void removeUser(final String email) throws DAOException {
		transManager.doInTransaction(new TransactionOperation() {
			@Override
			public Object execute(Connection conn) throws DAOException {
				userDAO.removeUser(conn, email);
				return null;
			}
		});
	}
}
