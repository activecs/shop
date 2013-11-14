package ua.epam.dereza.shop.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOFactory;
import ua.epam.dereza.shop.db.dao.mysql.MysqlDAOFactory;
import ua.epam.dereza.shop.service.CaptchaServiceCookie;
import ua.epam.dereza.shop.service.CaptchaServiceHidden;
import ua.epam.dereza.shop.service.CaptchaServiceSession;
import ua.epam.dereza.shop.service.ImageService;
import ua.epam.dereza.shop.service.ImageServiceImpl;
import ua.epam.dereza.shop.service.OrderService;
import ua.epam.dereza.shop.service.OrderServiceImpl;
import ua.epam.dereza.shop.service.ProductService;
import ua.epam.dereza.shop.service.ProductServiceImpl;
import ua.epam.dereza.shop.service.UserService;
import ua.epam.dereza.shop.service.UserServiceImpl;

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
		initCaptcha(context);
		initDAO(context);
		initServices(context);
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		log.info("Application shutted down");
	}

	private void initDAO(ServletContext context){
		DAOFactory daoFactory = new MysqlDAOFactory();
		context.setAttribute(Constants.DAO_FACTORY, daoFactory);
	}

	/**
	 * Inits services
	 * 
	 * @param context
	 */
	private void initServices(ServletContext context){
		DAOFactory daoFactory = (DAOFactory)context.getAttribute(Constants.DAO_FACTORY);
		// userService
		UserService userService = new UserServiceImpl(daoFactory);
		context.setAttribute(Constants.SERVICE_USER, userService);

		// imageService
		String externalResources = context.getInitParameter(Constants.EXTERNAL_RESOURCES);
		ImageService imageService = new ImageServiceImpl(externalResources);
		context.setAttribute(Constants.SERVICE_IMAGE, imageService);

		// productService
		ProductService productService  = new ProductServiceImpl(daoFactory);
		context.setAttribute(Constants.SERVICE_PRODUCT, productService);

		// orderService
		OrderService orderService = new OrderServiceImpl(daoFactory);
		context.setAttribute(Constants.SERVICE_ORDER, orderService);
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
		case Constants.CAPTCHA_MODE_SESSION:
			context.setAttribute(Constants.CAPTCHA_SERVICE, new CaptchaServiceSession(captchaLifetime));
			break;
		case Constants.CAPTCHA_MODE_COOKIE:
			context.setAttribute(Constants.CAPTCHA_SERVICE,	new CaptchaServiceCookie(captchaLifetime));
			break;
		case Constants.CAPTCHA_MODE_HIDDEN:
			context.setAttribute(Constants.CAPTCHA_SERVICE, new CaptchaServiceHidden(captchaLifetime));
			break;
		default:
			if (log.isEnabledFor(Level.WARN))
				log.warn("Cannot determine necesary captchaMode-> will be chosen sessionMode");
			context.setAttribute(Constants.CAPTCHA_SERVICE,	new CaptchaServiceSession(captchaLifetime));
		}
		log.debug("Selected captchaMode ->"
				+ context.getAttribute(Constants.CAPTCHA_SERVICE).getClass()
				.getSimpleName());
	}
}
