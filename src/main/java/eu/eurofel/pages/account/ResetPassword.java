package eu.eurofel.pages.account;

import javax.naming.NamingException;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.entities.ResetPasswordVO;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.notification.NotificationApplication;
import eu.eurofel.services.notification.NotificationType;

@Private
@Secure
public class ResetPassword {

	@Property
	private ResetPasswordVO resetPassword = new ResetPasswordVO();

	@Inject
	private ComponentResources resources;

//	@Inject
//	private EAACaptchaService captchaService;

	@Inject
	private RequestGlobals requestGlobals;

	@Component
	private BeanEditForm create;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

	void onActivate() throws NamingException {
		resetPassword = new ResetPasswordVO();
	}

	public Link getImageURL() {

		return resources.createEventLink("image");
	}

	void onSuccess() throws Exception {
		
		// we need to retrieve the user account
		
		EAAAccount accounts = new EAAAccount();
		Notification notification = new Notification();
		NotificationApplication.getInstance().notify(NotificationType.MAIL,
				notification, accounts);
	}
}
