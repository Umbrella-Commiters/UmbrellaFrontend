package eu.eurofel.pages.account;

import java.util.UUID;

import javax.naming.NamingException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.entities.PasswordRetriever;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.EAAService;
import eu.eurofel.services.NotificationService;

@Secure
public class LostPassword {

	private static int PASSWORD_LENGTH = 12;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

	@Property
	private PasswordRetriever retriever;

	@InjectPage
	private EmailSent index;

	@Inject
	private EAAService service;

	@Inject
	private NotificationService notificationService;

	@Component
	private BeanEditForm lostpassword;

	@Inject
	private ComponentResources resources;

//	@Inject
//	private EAACaptchaService captchaService;

	@Inject
	private RequestGlobals requestGlobals;


	void onActivate() throws NamingException {
		retriever = new PasswordRetriever();
	}

	public Link getImageURL() {
		return resources.createEventLink("image");
	}

	void onValidateFromLostPassword() {

		// find the matching email and username
		EAAAccount acc = null;
		try {
			acc = new EAAAccount(service.findAccountByEmailAndUid(retriever.getEmail(), retriever.getUsername()));
		} catch (NamingException e) {
			e.printStackTrace();

			lostpassword.recordError("Error. Please check your values");
		}

		// if username and email are right...
		if (acc != null && acc.getUid() != null && acc.getEmail() != null && !acc.getUid().equals("") && !acc.getEmail().equals("")) {

			// generate a new UUID
			//String newPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
			String uuid = UUID.randomUUID().toString();
			
			if(service.addResetPwUUID(retriever.getUsername(), uuid)){
				
//				Notification notification = new Notification();
//				notification.setSubject("Umbrella Lost Password");
//				notification.setBody("Dear " + acc.getUid() + ",\n\nTo change your password please visit: <https://umbrellaid.org/euu/account/setnewpassword?uuid=" + uuid + ">\n\nYour Umbrella Team");
//				acc.setEAAResetPwUUID(uuid);
//				acc.setEmail(retriever.getEmail());
//				try{
//					notificationService.notify(notification, acc);
//				}
//				catch (Exception e) {
//					lostpassword.recordError("Your email address could not be contacted. Please try again later.");
//				}
				
			} else {
				lostpassword.recordError("Error in reseting your password");
			}

			// change the password
//			if (service.changePassword(retriever.getUsername(), newPassword)) {
//
//				// send notifications
//				Notification notification = new Notification();
//				notification.setSubject("Umbrella Lost Password");
//				notification.setBody("Dear " + acc.getUid() + ",\n\nYour new password is: " + newPassword + "\n\nYour Umbrella Team");
//				acc.set
//				try {
//					notificationService.notify(notification, acc);
//				} catch (Exception e) {
//					lostpassword.recordError("Your email address could not be contacted. Please try again later.");
//				}
//
//			} else {
//				lostpassword.recordError("Error in reseting your password");
//			}
		} else {

			lostpassword.recordError("Error. Please check your values");
		}

	}

	Object onSuccess() throws Exception {
		EAAAccount acc = null;
		try {
			acc = new EAAAccount(service.findAccountByEmailAndUid(retriever.getEmail(), retriever.getUsername()));
		} catch (NamingException e) {
			e.printStackTrace();

			lostpassword.recordError("Error. Please check your values");
		}

		// if username and email are right...
		if (acc != null && acc.getUid() != null && acc.getEmail() != null && !acc.getUid().equals("") && !acc.getEmail().equals("")) {

			// generate a new UUID
			//String newPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
			String uuid = UUID.randomUUID().toString();
			
			if(service.addResetPwUUID(retriever.getUsername(), uuid)){
				
				Notification notification = new Notification();
				notification.setSubject("Umbrella Lost Password");
				notification.setBody("Dear " + acc.getUid() + ",\n\nTo change your password please visit: <https://umbrellaid.org/euu/account/setnewpassword?uuid=" + uuid + ">\n\nYour Umbrella Team");
				acc.setEAAResetPwUUID(uuid);
				acc.setEmail(retriever.getEmail());
				try{
					notificationService.notify(notification, acc);
				}
				catch (Exception e) {
					lostpassword.recordError("Your email address could not be contacted. Please try again later.");
				}
				
			} else {
				lostpassword.recordError("Error in reseting your password");
			}

			// change the password
//			if (service.changePassword(retriever.getUsername(), newPassword)) {
//
//				// send notifications
//				Notification notification = new Notification();
//				notification.setSubject("Umbrella Lost Password");
//				notification.setBody("Dear " + acc.getUid() + ",\n\nYour new password is: " + newPassword + "\n\nYour Umbrella Team");
//				acc.set
//				try {
//					notificationService.notify(notification, acc);
//				} catch (Exception e) {
//					lostpassword.recordError("Your email address could not be contacted. Please try again later.");
//				}
//
//			} else {
//				lostpassword.recordError("Error in reseting your password");
//			}
		} else {

			lostpassword.recordError("Error. Please check your values");
		}
		return index;
	}
}
