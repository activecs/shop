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

import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.service.ImageService;

/**
 * Allow you to get product's photo
 * 
 * @author Eduard_Dereza
 *
 */
@WebServlet("/product/*")
public class ProductImage extends HttpServlet {

	private static final long serialVersionUID = -7053307835486728328L;
	private ImageService imageService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imageService = (ImageService) config.getServletContext().getAttribute(Constants.SERVICE_IMAGE);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String imageName = pathInfo;

		FileInputStream imageInput = imageService.getProductPhoto(imageName);
		OutputStream respOut = response.getOutputStream();
		byte s[] = new byte[4096];
		int byteCount = 0;
		while ((byteCount = (imageInput.read(s))) != -1)
			respOut.write(Arrays.copyOfRange(s, 0, byteCount));
		respOut.flush();
		respOut.close();
		imageInput.close();
	}

}
