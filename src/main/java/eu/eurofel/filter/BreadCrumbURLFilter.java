package eu.eurofel.filter;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class BreadCrumbURLFilter implements Filter {

	protected FilterConfig fcon = null;

	ResourceBundle urlMap = ResourceBundle.getBundle("urlmap");

	public void destroy() {
		fcon = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain arg2) throws IOException, ServletException {
		String id = ((String[]) req.getParameterMap().get("Child"))[0];

		if (resp instanceof HttpServletResponse) {
			HttpServletResponse hresp = (HttpServletResponse) resp;
			hresp.sendRedirect(urlMap.getObject(id).toString());
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		fcon = arg0;
	}

}
