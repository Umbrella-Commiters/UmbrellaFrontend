package eu.eurofel.services.impl;

import java.io.File;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import eu.eurofel.Messages;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.services.NotificationService;

public class EmailNotificationService implements NotificationService {

	public void notify(Notification notification, EAAAccount... accounts)
			throws Exception {
		// for each account

		if (accounts != null && notification != null) {
			for (EAAAccount account : accounts) {
				HtmlEmail  email = new HtmlEmail();
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
				email.setSubject(notification.getSubject());

				// if notification contains a layout then apply it
				if (notification.getLayout() != null
						&& !notification.getLayout().equals("")) {
					String mailBody = notification.getLayout();
					File logo = new File(Messages.getString("mail.template.logo1"));
					String cid = email.embed(logo);
					mailBody = mailBody.replace("###content###", notification.getBody());
					mailBody = mailBody.replace("umbrellaLOGO", "cid:"+cid);
					email.setHtmlMsg(mailBody);
					email.setTextMsg("Your email client does not support HTML messages");

				} else {
					email.setTextMsg(notification.getBody());
				}
				email.addTo(account.getEmail());
//				System.out.println("Send E-Mail to: " + account.getEmail());

				email.send();
			}
		}

	}
}
