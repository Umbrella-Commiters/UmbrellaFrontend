package eu.eurofel.pages.account;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;

import eu.eurofel.components.SimpleDateField;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.UserSession;
import eu.eurofel.pages.Index;
import eu.eurofel.services.AffiliationService;
import eu.eurofel.services.EAAService;
import eu.eurofel.util.EAAHash;

@Secure
public class CreateAccount {

	@SuppressWarnings("unused")
	@InjectPage
	private Index index;

	@SuppressWarnings("unused")
	private static final String RESULT = "result";

	@SuppressWarnings("unused")
	@Inject
	private AffiliationService aservice;

	@Inject
	private EAAService service;

	@Property
	private EAAAccount eAAAccount = new EAAAccount();

	@InjectPage
	private CreateAccountSuccess success;

	@Inject
	private Request _request;

	@Inject
	private Response _response;

	@Component
	private BeanEditForm create;
//
//	@InjectComponent
//	private Zone dateZone;

	@InjectComponent(value = "uid")
	private TextField uidField;

//	@InjectComponent(value = "birthdate")
//	private SimpleDateField dateField;

	// @InjectComponent(value = "verificationCode")
	// private TextField verificationField;

	@InjectComponent(value = "email")
	private TextField emailField;

	@InjectComponent(value = "password")
	private PasswordField passwordField;

	@Inject
	private ComponentResources resources;
	//
	// @Inject
	// private EAACaptchaService captchaService;

	@Inject
	private RequestGlobals requestGlobals;

	private String target;

	@SuppressWarnings("unused")
	@SessionState(create = true)
	private UserSession userSession;

	private Pattern usernamePattern = Pattern.compile("[a-zA-Z0-9]{4,}");

	void onActivate() throws NamingException {

		// retrieve GET / POST parameters for upgrade account
		String username = _request.getParameter("username");
		String mail = _request.getParameter("mail");
//		String bdate = _request.getParameter("bdate");
		target = _request.getParameter("target");
		userSession.setTarget(target);

		eAAAccount = new EAAAccount();

		if (target != null && !target.equals("")) {
			eAAAccount.setTarget(target);
		}
		if (username != null && !username.equals("")) {
			eAAAccount.setUid(username);
		}
		if (mail != null && !mail.equals("")) {
			eAAAccount.setEmail(mail);
		}
//		Date dt = getDate(bdate);
//		if (dt != null) {
//			eAAAccount.setBirthdate(dt);
//		}
	}

	public Link getImageURL() {

		return resources.createEventLink("image");
	}

//	JSONObject onAjaxValidateFromBirthdate() {
//		String birthdate = _request.getParameter("param");
//
//		Date dt = getDate(birthdate);
//
//		if (dt == null) {
//			return new JSONObject().put("error", "DateFormat mismatch!: " + birthdate);
//		}
//
//		return new JSONObject();
//	}

	JSONObject onAjaxValidateFromUid() {

		String uid = _request.getParameter("param");
		try {
			service.isAccountAvailable(uid);
		} catch (Exception e) {
			return new JSONObject().put("error", e.getMessage());
		}
		if (isInappropriate(uid)) {
			return new JSONObject().put("error", "This is a reserved word");
		}
		// Check for regexp
		if (!usernamePattern.matcher(uid).matches()) {
			return new JSONObject().put("error", "Only alphanumeric characters are allowed and a minimum lenght of 4 characters is required!");
		}

		return new JSONObject();
	}

	private boolean isInappropriate(String uid) {

		for (String cand : getReservedWords()) {
			String candL = cand.toLowerCase();
			String uidL = uid.toLowerCase();
			if (candL.contains(uidL) || candL.equals(uidL) || candL.startsWith(uidL) || candL.endsWith(uidL) || uidL.contains(candL) || uidL.equals(candL)
					|| uidL.startsWith(candL) || uidL.endsWith(candL)) {

				return true;
			}
		}
		return false;
	}

	private Collection<String> getReservedWords() {
		ArrayList<String> list = new ArrayList<String>();
		list.add("root");
		list.add("admin");
		list.add("administrator");
		list.add("sex");
		return list;
	}

	JSONObject onAjaxValidateFromEmail() {
		String email = _request.getParameter("param");
		email = email.toLowerCase();
		try {
			service.isEmailAvailable(email);
		} catch (Exception e) {
			return new JSONObject().put("error", e.getMessage());
		}

		return new JSONObject();
	}

	void onValidateFromCreate() {
		// String sessionId =
		// requestGlobals.getHTTPServletRequest().getSession().getId();
		// if
		// (!captchaService.isValidUserResponse(eAAAccount.getVerificationCode(),
		// sessionId)) {
		// create.recordError(verificationField,
		// "Verification code you entered does not match");
		// return;
		// }
		if (!eAAAccount.getPassword().equals(eAAAccount.getPassword1())) {
			create.recordError(passwordField, "Passwords are not equal.");
		}
		/*
		if (eAAAccount.getBirthdate() == null) {
			create.recordError(dateField, "Please specify a valid birthdate!");
		}
		*/
		String uid = eAAAccount.getUid();
		try {
			service.isAccountAvailable(uid);
		} catch (Exception e) {
			create.recordError(uidField, "UID already exists.");
		}
		String email = eAAAccount.getEmail();
		try {
			System.err.println("EMail available: " + email + " :: " + EAAHash.getSHA1Hash(email) + service.isEmailAvailable(email));
			if (service.isEmailAvailable(email)) {
				create.recordError(emailField, "Email address already exists.");
				return;
			}
		} catch (Exception e) {
			create.recordError(emailField, "Email address already exists.");

			return;
		}
		if (isInappropriate(uid)) {
			create.recordError(uidField, "UID is inappropriate");
		}
		// Check for regexp
		if (!usernamePattern.matcher(uid).matches()) {
			create.recordError(uidField, "Only alphanumeric characters are allowed and a minimum lenght of 4 characters is required!");
		}

	}

	/*
	 * List<String> onProvideCompletionsFromPostalAddress(String partial) {
	 * List<String> matches = new ArrayList<String>(); partial =
	 * partial.toUpperCase();
	 * 
	 * for (Affiliation affl : (List<Affiliation>) aservice.getAffiliations()) {
	 * if (affl.toString().toLowerCase().contains(partial.toLowerCase())) {
	 * matches.add(affl.toString()); } }
	 * 
	 * return matches; }
	 */

	Object onSuccess() throws Exception {
		eAAAccount.setEaahash(UUID.randomUUID().toString());
		eAAAccount.setEaakey(UUID.randomUUID().toString());
		eAAAccount.setBirthdate(new Date());
		service.createAccount(eAAAccount);
//		System.out.println("Target: " + eAAAccount.getTarget());
		eAAAccount = new EAAAccount();
		return success;
	}

	public static Set<DateFormat> getDateFormats() {
		Set<DateFormat> formats = new HashSet<DateFormat>();
		formats.add(DateFormat.getDateInstance());
		formats.add(DateFormat.getDateInstance(DateFormat.LONG));
		formats.add(DateFormat.getDateInstance(DateFormat.MEDIUM));
		formats.add(DateFormat.getDateInstance(DateFormat.SHORT));
		formats.add(DateFormat.getDateInstance(DateFormat.SHORT, Locale.US));
		formats.add(DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN));
		formats.add(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ch")));
		formats.add(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("sv")));

		return formats;
	}

//	Object onBirthdateChanged() {
//		String date = _request.getParameter("param");
//		Date dt = null;
//		if (date == null) {
//			date = "";
//		} else {
//			dt = getDate(date);
//			if (dt != null) {
//				eAAAccount.setBirthdate(dt);
//			}
//		}
//		return _request.isXHR() ? dateZone.getBody() : null;
//	}

//	public String getDateFormatted() {
//		if (eAAAccount != null && eAAAccount.getBirthdate() != null) {
//			return DateFormat.getDateInstance(DateFormat.LONG).format(eAAAccount.getBirthdate());
//		}
//		return "";
//	}

	public Date getDate(String date) {
		Date dt = null;
		loop: for (DateFormat df : getDateFormats()) {
			try {
				dt = df.parse(date);
				if (dt != null) {
					break loop;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dt;
	}
}
