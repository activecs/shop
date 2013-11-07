package ua.epam.dereza.shop.filter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.bean.User;
import ua.epam.dereza.shop.core.Constants;
import ua.epam.dereza.shop.security.SecurityRule;
import ua.epam.dereza.shop.security.RulesParser;

/**
 * Servlet Filter implementation class Security
 */
public class SecurityFilter implements Filter {

	List<SecurityRule> rules;
	ServletContext context;

	@Override
	public void init(FilterConfig config) throws ServletException {
		context = config.getServletContext();
		String securityFilename = config.getInitParameter(Constants.SECURITY_FILENAME);
		String securityValidationSchema = config.getInitParameter(Constants.SECURITY_VALIDATION_SCHEMA);

		try {
			RulesParser parser = new RulesParser(securityFilename, securityValidationSchema);
			rules = parser.parseXml();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		SecurityRule actualRule = findRuleForUrI(request);
		if(actualRule != null){
			User userBean = (User) request.getSession().getAttribute(Constants.BEAN_USER);
			if (userBean == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.PAGE_LOGIN);
				dispatcher.forward(request, response);
				return;
			}else if(!hasPermission(actualRule, userBean)){
				response.sendError(403);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	boolean hasPermission(SecurityRule rule, User userBean){
		Set<User.Role> allowableRoles = rule.getRoles();
		return allowableRoles.contains(userBean.getRole());
	}

	SecurityRule findRuleForUrI(HttpServletRequest request){
		SecurityRule rule = null;
		for (SecurityRule currentRule : rules) {
			if (checkUriCoincidence(currentRule, request.getRequestURI())) {
				rule = currentRule;
			}
		}
		return rule;
	}

	boolean checkUriCoincidence(SecurityRule rule, String requestUri){
		// removes context path
		requestUri = requestUri.replaceAll(context.getContextPath(), "");
		// removes url rewriting
		requestUri = requestUri.replaceAll(";\\S[^\\?]{0,}", "");

		String urlPatternRegex = rule.getUrlPattern();

		return requestUri.matches(urlPatternRegex);
	}
}
