package eu.eurofel.pages.account;

import javax.naming.NamingException;

import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.PasswordRetriever;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.EAAService;

@Secure
public class SetNewPassword {

	@Property
	private PasswordRetriever retriever;

	@Inject
	private ComponentResources resources;

	// @Inject
	// private EAACaptchaService captchaService;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private EAAService service;

	@Inject
	private Block _valid;

	@Inject
	private Block _invalid;

	@Component
	private BeanEditForm create;

	private boolean valid = false;

	@Property
	@Persist
	private String uuid;

	@SessionState(create = true)
	private UserSession userSession;

	@InjectPage
	private ChangePasswordSuccess success;

	void onActivate() throws NamingException {
		retriever = new PasswordRetriever();
		uuid = requestGlobals.getRequest().getParameter("uuid");
		System.out.println("UUID: " + uuid);
		System.out.println("UserSessionUUID: " + userSession.getResetUUID());
		if (uuid != null) {
			userSession.setResetUUID(uuid);
		}
		// check
		if (service.checkForValidResetUUID(userSession.getResetUUID())) {
			valid = true;
		}

	}

	public Object getLayout() {
		if (valid) {
			return _valid;
		} else {
			return _invalid;
		}
	}

	void onValidateFromCreate() throws NamingException {

		// find the matching email and username
		EAAAccount acc = null;
		try {
			System.out.println(uuid);
			System.out.println(userSession.getResetUUID());
			acc = new EAAAccount(service.findAccountByResetUUID(userSession.getResetUUID(), retriever.getUsername(), retriever.getEmail()));
		} catch (NamingException e) {
			e.printStackTrace();

			create.recordError("Error. Please check your values");
		}

		if (acc != null && acc.getUid() != null && acc.getEmail() != null && !acc.getUid().equals("") && !acc.getEmail().equals("")) {

			service.changePassword(retriever.getUsername(), retriever.getPassword());
			// reset values
			service.removeResetPwUUID(acc.getUid());
		}

	}

	Object onSuccess() throws Exception {
		return success;

	}
}
