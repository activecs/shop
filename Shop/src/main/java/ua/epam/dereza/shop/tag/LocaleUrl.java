package ua.epam.dereza.shop.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Generates correct links for changing locale(?lang=.. or ?..&lang=..)
 * 
 * @author Eduard_Dereza
 * 
 */
public class LocaleUrl extends SimpleTagSupport {

	private PageContext pageContext;
	private JspWriter writer;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String lang;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	@Override
	public void doTag() throws JspException, IOException {
		writer = getJspContext().getOut();
		pageContext = (PageContext) getJspContext();
		request = (HttpServletRequest) pageContext.getRequest();
		response = (HttpServletResponse) pageContext.getResponse();

		String queryString = request.getQueryString();
		queryString = queryString != null ? queryString : "";
		queryString = queryString.replaceAll("(&|)lang=\\w{2}", "");

		if (!queryString.isEmpty())
			queryString = "?" + queryString + "&" + "lang=" + lang;
		else
			queryString = "?lang=" + lang;

		queryString = response.encodeURL(queryString);
		writer.write(queryString);
	}
}
