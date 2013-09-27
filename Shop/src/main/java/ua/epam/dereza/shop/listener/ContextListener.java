package ua.epam.dereza.shop.listener;

import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dao.UserDAOImpl;
import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.service.CaptchaServiceCookie;
import ua.epam.dereza.shop.service.CaptchaServiceSession;

/**
 * Application Lifecycle Listener implementation class ContextListener
 * 
 */
@WebListener
public class ContextListener implements ServletContextListener {

	private static final Logger log = Logger.getLogger(ContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg) {
		log.info("Application started");

		// captcha's store mode initialization
		String captchaMode = arg.getServletContext().getInitParameter(Constants.CAPTCHA_MODE);
		switch (captchaMode) {
		case Constants.CAPTCHA_MODE_SESSION: {
			arg.getServletContext().setAttribute(Constants.CAPTCHA_SERVICE, new CaptchaServiceSession());
			break;
		}
		case Constants.CAPTCHA_MODE_COOKIE: {
			arg.getServletContext().setAttribute(Constants.CAPTCHA_SERVICE, new CaptchaServiceCookie());
			break;
		}
		default: {
			arg.getServletContext().setAttribute(Constants.CAPTCHA_SERVICE, new CaptchaServiceSession());
		}
		}

		// dao initialization
		UserDAO userDAO = new UserDAOImpl();
		userDAO.saveUser(new UserDTO("user1", true, "", "", "user1", new Date(), "", "", "", "", 1, "", ""));
		userDAO.saveUser(new UserDTO("user2", true, "", "","user2", new Date(), "", "", "", "", 1, "", ""));
		userDAO.saveUser(new UserDTO("user3", true, "", "",  "user3", new Date(), "", "", "", "", 1, "", ""));
		userDAO.saveUser(new UserDTO("user4", true, "", "", "user4", new Date(), "", "", "", "", 1, "", ""));
		userDAO.saveUser(new UserDTO("user5", true, "", "", "user5", new Date(), "", "", "", "", 1, "", ""));
		userDAO.saveUser(new UserDTO("user6", true, "", "", "user6", new Date(), "", "", "", "", 1, "", ""));
		arg.getServletContext().setAttribute(Constants.USER_DAO, userDAO);
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		log.info("Application shutted down");
	}

}
