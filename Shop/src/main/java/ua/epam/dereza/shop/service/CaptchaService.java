package ua.epam.dereza.shop.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Basic abstract class for captchaService
 * 
 * @author Eduard_Dereza
 *
 */
public abstract class CaptchaService {

	private static final int CAPTCHA_WIDTH = 150;
	private static final int CAPTCHA_HEIGHT = 50;
	private static final int CAPTCHA_MIN_LENGTH = 5;
	private static final int CAPTCHA_MAX_LENGTH = 8;
	private static final int START_CHAR_CODE = 65;
	private static final int LAST_CHAR_CODE = 90;
	protected static final int COOKIE_LIFETIME = 300;

	/**
	 * generates keyword for captcha
	 * 
	 * @return keyword
	 */
	public String generateKeyword(){
		Random rand = new Random();
		int captchaLength = CAPTCHA_MIN_LENGTH + rand.nextInt(CAPTCHA_MAX_LENGTH - CAPTCHA_MIN_LENGTH);
		char captchaArr[] = new char[captchaLength];
		for (int i = 0; i < captchaLength; i++) {
			int nextCode = START_CHAR_CODE + rand.nextInt(LAST_CHAR_CODE - START_CHAR_CODE);
			captchaArr[i] = (char) nextCode;
		}

		return new String(captchaArr);
	}

	/**
	 * Generates image for captcha
	 * 
	 * @param keyword
	 * @return captcha image
	 */
	public BufferedImage generateImage(String keyword) {
		BufferedImage bufferedImage = new BufferedImage(CAPTCHA_WIDTH,
				CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = bufferedImage.createGraphics();

		Font font = new Font("Georgia", Font.BOLD, 18);
		graphics.setFont(font);

		RenderingHints rendering = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rendering.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		graphics.setRenderingHints(rendering);

		GradientPaint gradient = new GradientPaint(0, 0, Color.cyan, 0,
				CAPTCHA_HEIGHT / 2, Color.green, true);

		graphics.setPaint(gradient);
		graphics.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

		graphics.setColor(new Color(255, 153, 0));

		// draws image
		Random rand = new Random();
		int x = 0;
		int y = 0;
		for (int i = 0; i < keyword.length(); i++) {
			x += 10 + (Math.abs(rand.nextInt()) % 15);
			y = 20 + Math.abs(rand.nextInt()) % 20;
			graphics.drawChars(keyword.toCharArray(), i, 1, x, y);
		}
		graphics.dispose();

		return bufferedImage;
	}

	/**
	 * Validates captcha
	 * 
	 * @param request
	 * @return list of errors
	 */
	public abstract List<String> validateCaptcha(HttpServletRequest request);

	/**
	 * Saves encoded captcha into session attribute or in cookie according
	 *  to captcha's store Mode
	 * 
	 * @param keyword
	 * @param request
	 * @param response
	 */
	public abstract void attachCaptcha(String keyword, HttpServletRequest request, HttpServletResponse response);
}
