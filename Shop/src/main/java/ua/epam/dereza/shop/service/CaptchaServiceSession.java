package ua.epam.dereza.shop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.bean.RegistrationForm;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.util.Cryptographer;

/**
 * CaptchaService for session
 * 
 * @author Eduard_Dereza
 *
 */
public class CaptchaServiceSession extends CaptchaService {

	public CaptchaServiceSession(int captchaLifetime) {
		super(captchaLifetime);
	}

	@Override
	public List<String> validateCaptcha(HttpServletRequest request, RegistrationForm formBean) {
		// cleans expired captchas
		cleanCaptchaMaps();

		List<String> errors = new ArrayList<String>();
		String expectedCaptcha = (String) request.getSession().getAttribute(Constants.CAPTCHA_ID);
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

		// adds encoded captcha to session's attributes
		request.getSession().setAttribute(Constants.CAPTCHA_ID, encodedCaptcha);

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
