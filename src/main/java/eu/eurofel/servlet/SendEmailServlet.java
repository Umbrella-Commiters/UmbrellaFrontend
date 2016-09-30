package eu.eurofel.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import eu.eurofel.Messages;
import eu.eurofel.util.ReCaptchaValidate;

public class SendEmailServlet implements Filter {

	protected FilterConfig fcon = null;

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) {
		try {
			doPost((HttpServletRequest) req, (HttpServletResponse) res);
		} catch (Exception e) {

		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String message = req.getParameter("message");
		String gresponse = req.getParameter("g-recaptcha-response");
		String ip = req.getRemoteAddr();

		// verify captcha
		if (ReCaptchaValidate.validate("", gresponse, ip)) {
			try {
				sendMail(name, email, message);
			} catch (Exception e) {

			}

		}
		resp.sendRedirect("https://umbrellaid.org/euu/account/emailsent");

	}

	private void sendMail(String name, String mail, String message)
			throws EmailException {

		HtmlEmail email = new HtmlEmail();
		email.setHostName(Messages.getString("mail.host")); //$NON-NLS-1$
		email.setSmtpPort(25);
		if (!Messages.getString("mail.username").equals("")
				&& !Messages.getString("mail.password").equals("")) {
			email.setAuthenticator(new DefaultAuthenticator(
					Messages.getString("mail.username"), Messages.getString("mail.password"))); //$NON-NLS-1$ //$NON-NLS-2$
		}
		// email.setTLS(false);
		// email.setSSL(true);
		email.setFrom(Messages.getString("mail.from")); //$NON-NLS-1$
		email.setSubject("Umbrella Webpage Mail");

		// if notification contains a layout then apply it

		email.setTextMsg("From: " + name + "\nEmail:" + mail + "\nMessage:\n"
				+ message);
		email.addTo("contact@umbrellaid.org");

		email.send();
	}

	public void init(FilterConfig arg0) throws ServletException {
		fcon = arg0;
	}

	@Override
	public void destroy() {
		fcon = null;
	}
}
