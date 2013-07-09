package eu.eurofel.filter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CMISDownloadFilter implements Filter {

	protected FilterConfig fcon = null;

	public void destroy() {
		fcon = null;
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) resp;
		String url = req.getParameter("url");
		String name = req.getParameter("name");
		int length = 0;

		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
		OutputStream op = res.getOutputStream();
		//
		// Stream to the requester.
		//
		byte[] bbuf = new byte[2048];
		DataInputStream in = new DataInputStream(new URL(url).openStream());

		while ((in != null) && ((length = in.read(bbuf)) != -1)) {
			op.write(bbuf, 0, length);
		}

		in.close();
		op.flush();
		op.close();

	}

	public void init(FilterConfig fcon) throws ServletException {
		this.fcon = fcon;
	}

}
