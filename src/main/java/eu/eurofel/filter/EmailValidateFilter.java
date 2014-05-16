package eu.eurofel.filter;

import java.io.IOException;

import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import eu.eurofel.Messages;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.services.EAAService;

public class EmailValidateFilter implements Filter {

	protected FilterConfig fcon = null;

	public void destroy() {
		fcon = null;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		String uuid = req.getParameter("uuid");
		String uid = req.getParameter("uid");
		String target = req.getParameter("target");
		boolean found = false;

		// cast the ServletRequest to a HttpServletRequest

		HttpServletResponse srvResp = (HttpServletResponse) res;

		if (uuid != null && uid != null) {

			Resource resource = new UrlResource("file://"
					+ Messages.getString("application.context.path"));
			BeanFactory factory = new XmlBeanFactory(resource);
			EAAService service = (EAAService) factory.getBean("eAAService");
			try {
				EAAAccount acc = new EAAAccount(service.findAccount(uid));
				String eaahash = service.findAccount(uid).get("EAAHash").get()
						.toString();
				// do a check if the UUID is right and if yes, activate the
				// account
				if (eaahash.equals(uuid)) {
					service.activateAccount(acc);
					found = true;
					// if target is set, redirect to it
					if(target != null && !target.equals("") && !target.equals("")){
						srvResp.sendRedirect(target);
					}
					else{
						srvResp.sendRedirect("/euu/account/activated");
					}
				}

			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		if (!found) {
			srvResp.sendRedirect("/euu/account/notactivated");
		}
	}

	public void init(FilterConfig fcon) throws ServletException {
		this.fcon = fcon;
	}
//
//	private String getActivatedHtml() {
//		return "<html><head><meta http-equiv=\"REFRESH\" content=\"0; url=/euu/account/activated\" /></head><body /></html>";
//	}
//
//	private String getNotActivatedHtml() {
//		return "<html><head><meta http-equiv=\"REFRESH\" content=\"0; url=/euu/account/notactivated\" /></head><body /></html>";
//	}
}
