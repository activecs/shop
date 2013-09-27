package ua.epam.dereza.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.epam.dereza.shop.util.Cryptographer;

/**
 * CaptchaService for session
 * 
 * @author Eduard_Dereza
 *
 */
public class CaptchaServiceSession extends CaptchaService {

	@Override
	public List<String> validateCaptcha(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();

		// determine captcha's store mode and checks it
		String expectedCaptcha = (String) request.getSession().getAttribute(
				"expectedCaptcha");
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

		// adds encoded captcha to session's attributes
		request.getSession().setAttribute("expectedCaptcha", encodedCaptcha);

	}

}
