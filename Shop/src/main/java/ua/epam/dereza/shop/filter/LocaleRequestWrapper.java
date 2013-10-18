package ua.epam.dereza.shop.filter;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * requestWrapper with correct locale
 * 
 * @author Eduard_Dereza
 * 
 */
public class LocaleRequestWrapper extends HttpServletRequestWrapper {

	private Locale locale;
	private Vector<Locale> locales;

	public LocaleRequestWrapper(HttpServletRequest request, Locale locale) {
		super(request);
		this.locale = locale;
		locales = new Vector<>();
		locales.add(locale);
	}

	@Override
	public String getHeader(String name) {
		if(name.equalsIgnoreCase("accept-language"))
			return locale.getLanguage();

		return super.getHeader(name);
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return locales.elements();
	}

	@Override
	public Locale getLocale() {
		return locale;
	}
}
