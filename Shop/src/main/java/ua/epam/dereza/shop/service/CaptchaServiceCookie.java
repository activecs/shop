package ua.epam.dereza.shop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.bean.RegistrationForm;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.util.Cryptographer;

/**
 * CaptchaService for cookie
 * 
 * @author Eduard_Dereza
 *
 */
public class CaptchaServiceCookie extends CaptchaService {

	public CaptchaServiceCookie(int captchaLifetime) {
		super(captchaLifetime);
	}

	@Override
	public List<String> validateCaptcha(HttpServletRequest request, RegistrationForm formBean) {
		// cleans expired captchas
		cleanCaptchaMaps();

		List<String> errors = new ArrayList<String>();
		// determine captcha's store mode and checks it
		String expectedCaptcha = null;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(Constants.CAPTCHA_ID))
				expectedCaptcha = cookie.getValue();
		}
		String formCaptcha = formBean.getCaptcha();

		if(formCaptcha == null || expectedCaptcha == null)
			errors.add("Check captcha");

		if(errors.isEmpty()){
			if(!expectedCaptcha.equals(Cryptographer.encode(formCaptcha)))
				errors.add("Check captcha");
		}

		if(errors.isEmpty()){
			if(pairs.get(expectedCaptcha) == null)
				errors.add("Captcha expired");
		}

		return errors;
	}

	@Override
	public void attachCaptcha(String keyword, HttpServletRequest request,
			HttpServletResponse response) {
		String encodedCaptcha = Cryptographer.encode(keyword);

		// adds encoded captcha to cookies
		Cookie cookie = new Cookie(Constants.CAPTCHA_ID, encodedCaptcha);
		response.addCookie(cookie);

		// added for checking dates
		synchronized (pairs) {
			pairs.put(encodedCaptcha, new Date());
		}
	}

	@Override
	public String getKeyword(String captchaID) {
		throw new UnsupportedOperationException();
	}

}
