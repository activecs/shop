package ua.epam.dereza.shop.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.service.ImageService;

/**
 * Allows you to access to external resources(such as avatars)
 * 
 * @author Eduard_Dereza
 *
 */
@WebServlet("/avatar.png")
public class Avatar extends HttpServlet {

	private static final long serialVersionUID = 1741249551192887320L;
	private static final Logger log = Logger.getLogger(Avatar.class);
	private ImageService imageService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imageService = (ImageService) config.getServletContext().getAttribute(Constants.SERVICE_IMAGE);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User userBean = (User) session.getAttribute(Constants.BEAN_USER);
		if(userBean == null){
			response.sendError(404);
			log.warn("Cannot find avatar because userBean == null");
			return;
		}

		FileInputStream avatarInput = imageService.getAvatar(userBean);
		OutputStream respOut = response.getOutputStream();
		byte s[] = new byte[4096];
		int byteCount = 0;
		while ((byteCount = (avatarInput.read(s))) != -1)
			respOut.write(Arrays.copyOfRange(s, 0, byteCount));
		respOut.flush();
		respOut.close();
		avatarInput.close();
	}
}