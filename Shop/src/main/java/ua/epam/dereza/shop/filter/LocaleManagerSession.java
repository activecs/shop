package ua.epam.dereza.shop.filter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LocaleManager implementation for storing locale in session
 * 
 * @author Eduard_Dereza
 *
 */
public class LocaleManagerSession implements LocaleManager {

	@Override
	public void setLocale(Locale locale, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("lang", locale);
	}

	@Override
	public Locale getLocale(HttpServletRequest request) {
		Locale locale = null;
		HttpSession session = request.getSession();
		locale = (Locale) session.getAttribute("lang");
		return locale;
	}
}
