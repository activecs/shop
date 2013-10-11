package ua.epam.dereza.shop.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds page generation time and makes footer red if page generation time is
 * more than MAX_DELAY
 * 
 * @author Eduard_Dereza
 *
 */
public class FooterFilter implements Filter {

	private static final long MAX_DELAY = 200;

	public FooterFilter() {
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		ResponseBuffer wrappedResp = new ResponseBuffer(response);

		long startTime = new Date().getTime();
		chain.doFilter(request, wrappedResp);
		long finishTime = new Date().getTime();

		response.setContentType(wrappedResp.getContentType());

		byte[] content = wrappedResp.getBytes();
		if (contentTypeText(wrappedResp)) {
			// page generation time
			long delay = finishTime - startTime;
			content = doModification(wrappedResp, delay);
		}
		ServletOutputStream out = response.getOutputStream();
		out.write(content);
		out.flush();
		out.close();
	}

	/**
	 * Checks whether content type is text
	 * 
	 * @param response
	 * @return true if content type is text
	 */
	private boolean contentTypeText(HttpServletResponse response) {
		String contentType = response.getContentType();
		if(contentType != null)
			return contentType.contains("text/html");
		return false;
	}

	/**
	 * Adds page generation time and makes footer red if page generation time is
	 * more than MAX_DELAY
	 * 
	 * @param wrappedResp
	 * @param delay
	 * @return modificated bytes from wrapped response
	 * @throws IOException
	 */
	private byte[] doModification(ResponseBuffer wrappedResp, long delay) throws IOException {
		final String RED_FOOTER_WITH_DELAY = "<footer style=\"background-color:#FF0000;\"> <p>Generation time = " + delay + " ms</p>";
		final String FOOTER_WITH_DELAY ="<footer> <p>Generation time = " + delay + " ms</p>";

		byte[] content = wrappedResp.getBytes();
		String contentStr = new String(content,	wrappedResp.getCharacterEncoding());
		if (delay > MAX_DELAY) {
			contentStr = contentStr.replace("<footer>",	RED_FOOTER_WITH_DELAY);
		} else {
			contentStr = contentStr.replace("<footer>", FOOTER_WITH_DELAY);
		}
		content = contentStr.getBytes();

		return content;
	}

}
