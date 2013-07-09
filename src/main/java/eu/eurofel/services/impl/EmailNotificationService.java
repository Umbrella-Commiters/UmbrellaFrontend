package eu.eurofel.services.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

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
				Email email = new SimpleEmail();
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
				email.setMsg(notification.getBody()); 

				email.addTo(account.getEmail());

				email.send();
			}
		}

	}
}
