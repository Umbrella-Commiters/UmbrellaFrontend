package eu.eurofel.pages;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.NotificationService;

public class Lxp {

	@SessionState(create = false)
	private UserSession userSession;

    @Inject
    private NotificationService notificationService;
    
	public String getUsername() throws Exception {

		if (userSession != null && userSession.isLoggedIn() && !userSession.getUserName().equals("")) {
			String username = userSession.getUserName();
			Notification notification = new Notification();
			
			notification.setSubject(username + " registered for OpenIRIS");
			notification.setBody(username + " registered for OpenIRIS");
			
			EAAAccount acc1 = new EAAAccount();
			acc1.setEmail("bjoern.abt@psi.ch");
			notificationService.notify(notification, acc1);
			
			return username;
		}
		return "";
	}
}
