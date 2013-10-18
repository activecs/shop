package ua.epam.dereza.shop.filter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manager that allows you set and get locale from request
 * 
 * @author Eduard_Dereza
 *
 */
public interface LocaleManager {

	public void setLocale(Locale locale, HttpServletRequest request, HttpServletResponse response);

	public Locale getLocale(HttpServletRequest request);
}
