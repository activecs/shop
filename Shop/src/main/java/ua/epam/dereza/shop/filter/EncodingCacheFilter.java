package ua.epam.dereza.shop.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.epam.dereza.shop.core.Constants;

/**
 * Sets characterEncoding if it is necessary and turns off cache-response
 * 
 * @author Eduard_Dereza
 * 
 */
public class EncodingCacheFilter implements Filter {

	public EncodingCacheFilter() {
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String encoding = request.getServletContext().getInitParameter(Constants.ENCODING);

		if (request.getCharacterEncoding() == null)
			request.setCharacterEncoding(encoding);

		response.setCharacterEncoding(encoding);
		response.setHeader("cache-response-directive", "no-cache");

		chain.doFilter(request, response);
	}

}
