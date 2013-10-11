package ua.epam.dereza.shop.listener;

import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.UserDAO;
import ua.epam.dereza.shop.db.dao.UserDAOImpl;
import ua.epam.dereza.shop.db.dto.UserDTO;
import ua.epam.dereza.shop.service.CaptchaServiceCookie;
import ua.epam.dereza.shop.service.CaptchaServiceHidden;
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
		ServletContext context = arg.getServletContext();
		initDAO(context);
		initCaptcha(context);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		log.info("Application shutted down");
	}

	/**
	 * Inits dao
	 * 
	 * @param context
	 */
	private void initDAO(ServletContext context) {
		// dao initialization
		UserDAO userDAO = new UserDAOImpl();
		userDAO.saveUser(new UserDTO(
				"user1@yandex.ru",
				"user1@yandex.ru.png",
				true,
				"Eduard",
				"Dereza",
				"24c9e15e52afc47c225b757e7bee1f9d",
				new Date(),
				"Home",
				"",
				"",
				"Kharkiv",
				61024,
				"When you need more functionality, something beyond what you can get with the standard actions or EL, you don’t have to resort to scripting. In the next chapter, you’ll learn how to use the JSP Standard Tag Library 1.1 (JSTL 1.1) to do just about everything you’ll ever need, using a combination of tags and EL. Here’s a sneak peek of how to do our conditional forward without scripting.",
				"0930238984", UserDTO.Role.USER, new Locale("ru")));
		userDAO.saveUser(new UserDTO("user2", "", true, "", "", "user2",
				new Date(), "", "", "", "", 1, "", "", UserDTO.Role.USER, new Locale("en")));
		userDAO.saveUser(new UserDTO("user3", "", true, "", "", "user3",
				new Date(), "", "", "", "", 1, "", "", UserDTO.Role.USER, new Locale("ru")));
		context.setAttribute(Constants.USER_DAO, userDAO);
	}

	/**
	 * Inits captcha processing mode
	 * 
	 * @param context
	 */
	private void initCaptcha(ServletContext context) {
		// captcha's store mode initialization
		String captchaMode = context.getInitParameter(Constants.CAPTCHA_MODE);
		Integer captchaLifetime = Integer.valueOf(context.getInitParameter(Constants.CAPTCHA_LIFETIME));
		switch (captchaMode) {
		case Constants.CAPTCHA_MODE_SESSION: {
			context.setAttribute(Constants.CAPTCHA_SERVICE,
					new CaptchaServiceSession(captchaLifetime));
			break;
		}
		case Constants.CAPTCHA_MODE_COOKIE: {
			context.setAttribute(Constants.CAPTCHA_SERVICE,
					new CaptchaServiceCookie(captchaLifetime));
			break;
		}
		case Constants.CAPTCHA_MODE_HIDDEN: {
			context.setAttribute(Constants.CAPTCHA_SERVICE,
					new CaptchaServiceHidden(captchaLifetime));
			break;
		}
		default: {
			if (log.isEnabledFor(Level.WARN))
				log.warn("Cannot determine necesary captchaMode-> will be chosen sessionMode");
			context.setAttribute(Constants.CAPTCHA_SERVICE,
					new CaptchaServiceSession(captchaLifetime));
		}
		}
		log.debug("Selected captchaMode ->"
				+ context.getAttribute(Constants.CAPTCHA_SERVICE).getClass()
				.getSimpleName());
	}
}
