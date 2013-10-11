package ua.epam.dereza.shop.core;

/**
 * Constants for app
 * 
 * @author Eduard_Dereza
 *
 */
public final class Constants {

	// path
	public static final String PAGE_REGISTRATION = "/WEB-INF/pages/registration.jsp";
	public static final String PAGE_LOGIN = "/WEB-INF/pages/login.jsp";
	public static final String PAGE_FORGET_PASSWORD = "/WEB-INF/pages/forgetpass.jsp";
	public static final String PAGE_CONTACT = "/WEB-INF/pages/contact.jsp";
	public static final String PAGE_ACCOUNT = "/WEB-INF/pages/account.jsp";

	//captcha
	public static final String  CAPTCHA_MODE = "captchaMode";
	public static final String  CAPTCHA_MODE_SESSION = "session";
	public static final String  CAPTCHA_MODE_COOKIE = "cookie";
	public static final String  CAPTCHA_MODE_HIDDEN = "hiddenField";
	public static final String  CAPTCHA_SERVICE = "captchaService";
	public static final String 	CAPTCHA_ID = "captchaID";

	// dao
	public static final String  USER_DAO = "userDAO";

	public static final String USER_BEAN = "userBean";

	// http header
	public static final String HEADER_REFERER = "referer";

	public static final String CAPTCHA_LIFETIME = "captchaLifetime";
	public static final String EXTERNAL_RESOURCES = "externalResources";

	// locale
	public static final String LOCALE_AVAILABLE = "availableLocales";
	public static final String LOCALE_DEFAULT = "defaultLocale";
	public static final String LOCALE_MODE = "localeMode";
	public static final String LOCALE_MODE_SESSION = "session";
	public static final String LOCALE_MODE_COOKIE = "cookie";
	public static final String LOCALE_LIFETIME = "localeLifetime";

	public static final String ENCODING = "encoding";
}
