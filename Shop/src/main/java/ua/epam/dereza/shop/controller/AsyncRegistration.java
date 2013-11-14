package ua.epam.dereza.shop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.db.dao.DAOException;
import ua.epam.dereza.shop.service.UserService;

/**
 * Servlet implementation class AsyncRegistration
 */
@WebServlet("/asyncRegistration")
public class AsyncRegistration extends HttpServlet {

	private static final long serialVersionUID = 6932752127641966309L;

	private UserService userService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		userService = (UserService)config.getServletContext().getAttribute(Constants.SERVICE_USER);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> resp = new HashMap<>();
		String email = request.getParameter("email");
		User user = null;
		String json = null;
		try {
			user = userService.findUserByEmail(email);
		} catch (DAOException e) {
			throw new ServletException(e);
		}
		if(user == null){
			resp.put("code", "0");
		}else{
			resp.put("code", "1");
			resp.put("message", "current email is already used by another user");
		}

		// writes json to response
		Gson gson = new Gson();
		json = gson.toJson(resp);
		response.setContentType("text/json");
		PrintWriter writer = response.getWriter();
		writer.write(json);
		writer.flush();
	}
}
