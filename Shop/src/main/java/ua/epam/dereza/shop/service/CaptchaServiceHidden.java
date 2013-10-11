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
 * Captcha service for hidden field
 * 
 * @author Eduard_Dereza
 *
 */
public class CaptchaServiceHidden extends CaptchaService {

	public CaptchaServiceHidden(int captchaLifetime) {
		super(captchaLifetime);
	}

	@Override
	public List<String> validateCaptcha(HttpServletRequest request,RegistrationForm formBean) {
		// cleans expired captchas
		cleanCaptchaMaps();

		List<String> errors = new ArrayList<String>();

		// determine captcha's store mode and checks it
		String expectedCaptcha = formBean.getExpectedCaptcha();
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

		String captchaID = Cryptographer.encode(keyword);
		synchronized (mapping) {
			mapping.put(captchaID, keyword);
		}
		synchronized (pairs) {
			pairs.put(captchaID, new Date());
		}

		request.setAttribute(Constants.CAPTCHA_ID, captchaID);
	}

	@Override
	public String getKeyword(String captchaID) {
		return mapping.get(captchaID);
	}

}
