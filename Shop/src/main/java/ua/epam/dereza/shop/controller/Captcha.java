package ua.epam.dereza.shop.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.service.CaptchaService;

/**
 * Generate new captcha image and saves md5 code of captcha's symbols in session, cookie or hidden field
 * 
 * @author Eduard_Dereza
 * 
 */
@WebServlet("/captcha.png")
public class Captcha extends HttpServlet {

	private static final long serialVersionUID = -1567800531379563125L;

	private CaptchaService captchaService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		captchaService = (CaptchaService)config.getServletContext().getAttribute(Constants.CAPTCHA_SERVICE);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String captchaKeyword = null;
		BufferedImage bufferedImage = null;

		if(getServletContext().getInitParameter(Constants.CAPTCHA_MODE).equals(Constants.CAPTCHA_MODE_HIDDEN)){
			String captchaID = request.getParameter(Constants.CAPTCHA_ID);
			captchaKeyword = captchaService.getKeyword(captchaID);
			bufferedImage = captchaService.generateImage(captchaKeyword);
		}else{
			captchaKeyword = captchaService.generateKeyword();
			bufferedImage = captchaService.generateImage(captchaKeyword);
			captchaService.attachCaptcha(captchaKeyword, request, response);
		}

		// write image into outputStream
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		os.close();
	}

}
