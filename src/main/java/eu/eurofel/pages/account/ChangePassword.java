package eu.eurofel.pages.account;

import javax.naming.NamingException;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.EmailRenewal;
import eu.eurofel.entities.PasswordRenewal;
import eu.eurofel.entities.UserSession;
import eu.eurofel.pages.Index;
import eu.eurofel.services.EAAService;

@Private
@Secure
public class ChangePassword {

	@Property
	@Persist
	private String eaahash;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

	@Property
	@Persist
	private String eaakey;

	@Property
	@Persist
	private String uid;

	@Inject
	private EAAService service;

	@Inject
	private Request _request;

	@SuppressWarnings("unused")
	@InjectPage
	private Index index;
	
	@InjectPage
	private EmailSent emailSent;

	@InjectPage
	private ChangePasswordSuccess success;

	@Property
	private EmailRenewal email = new EmailRenewal();

	@InjectComponent(value = "newEmail")
	private TextField emailField;

	@Property
	private PasswordRenewal password = new PasswordRenewal();

	@InjectComponent(value = "newPassword1")
	private PasswordField passwordField;

	@Component
	private BeanEditForm ChangeEmail;

	@Component
	private BeanEditForm ChangePassword;

	void onActivate() throws NamingException {
		password = new PasswordRenewal();
		password.setEaahash(_request.getHeader("EAAHash"));
		password.setEaakey(_request.getHeader("EAAKey"));
		eaahash = _request.getHeader("EAAHash");
		eaakey = _request.getHeader("EAAKey");
		uid = _request.getHeader("uid");

		email = new EmailRenewal();

	}

	void onValidateFromChangePassword() throws NamingException {
		if (!password.getNewPassword1().equals(password.getNewPassword2())) {
			ChangePassword.recordError(passwordField,
					"Passwords are not equal.");
		}
		// validate username and password
		// update password

	}

	Object onSuccessFromChangeEmail() throws Exception {
//		System.out.println("New Email: " + email.getEmail());
		try {
			service.changeEmail(uid, eaahash, email.getEmail());
		} catch (Exception e) {
			ChangeEmail.recordError(emailField, e.getLocalizedMessage());
		}
		return emailSent;
	}

	Object onSuccessFromChangePassword() throws Exception {
		// if (userSession != null && userSession.isLoggedIn()
		// && !userSession.getUserName().equals("")) {
		if (eaakey != null && eaahash != null) {
			if (service.changePassword(uid, password.getOldPassword(),
					password.getNewPassword1())) {
				return success;
			} else {
				ChangePassword.recordError(passwordField,
						"Error Changing Password");

			}
		}
		return this;
	}
}
