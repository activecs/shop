package ua.epam.dereza.shop.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Generates correct links for pagination
 * 
 * @author Eduard_Dereza
 *
 */
public class PaginationUrl extends SimpleTagSupport {

	private PageContext pageContext;
	private JspWriter writer;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String page;

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	@Override
	public void doTag() throws JspException, IOException {
		writer = getJspContext().getOut();
		pageContext = (PageContext) getJspContext();
		request = (HttpServletRequest) pageContext.getRequest();
		response = (HttpServletResponse) pageContext.getResponse();

		String queryString = request.getQueryString();
		queryString = queryString != null ? queryString : "";
		queryString = queryString.replaceAll("(&|)page=[^&]*", "");

		if (!queryString.isEmpty())
			queryString = "?" + queryString + "&" + "page=" + page;
		else
			queryString = "?page=" + page;

		queryString = response.encodeURL(queryString);
		writer.write(queryString);
	}
}
