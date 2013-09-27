package ua.epam.dereza.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.util.Cryptographer;

/**
 * CaptchaService for cookie
 * 
 * @author Eduard_Dereza
 *
 */
public class CaptchaServiceCookie extends CaptchaService {

	@Override
	public List<String> validateCaptcha(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		// determine captcha's store mode and checks it
		String expectedCaptcha = request.getParameter("expectedCaptcha");
		String formCaptcha = request.getParameter("captcha");

		if (formCaptcha == null
				|| !expectedCaptcha.equals(Cryptographer.encode(formCaptcha)))
			errors.add("Check captcha");

		return errors;
	}

	@Override
	public void attachCaptcha(String keyword, HttpServletRequest request,
			HttpServletResponse response) {
		String encodedCaptcha = Cryptographer.encode(keyword);

		// adds encoded captcha to cookies
		Cookie cookie = new Cookie("expectedCaptcha", encodedCaptcha);
		cookie.setMaxAge(COOKIE_LIFETIME);
		response.addCookie(cookie);
	}

}
