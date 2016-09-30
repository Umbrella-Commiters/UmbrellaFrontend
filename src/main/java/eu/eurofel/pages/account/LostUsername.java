package eu.eurofel.pages.account;

import java.io.File;
import java.util.HashMap;

import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import eu.eurofel.Messages;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.entities.PasswordRetriever;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.EAAService;
import eu.eurofel.services.NotificationService;

@Secure
public class LostUsername {

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
	private BeanEditForm lostusername;

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

	// executes when image is rendered
	/*
	public Object onImage() throws IOException {

		return new StreamResponse() {

			String contentType;

			
			public void prepareResponse(Response response) {
				// TODO Auto-generated method stub

			}

			
			public InputStream getStream() throws IOException {
				BufferedImage image = (BufferedImage) captchaService
						.getChallengeForID(requestGlobals
								.getHTTPServletRequest().getSession().getId());
				this.contentType = "PNG";
				// new StreamResponse("image/jpeg", null);
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				ImageIO.write(image, "PNG", byteArray);

				return new ByteArrayInputStream(byteArray.toByteArray());
			}

			
			public String getContentType() {
				return contentType;
			}
		};
	}
*/
	void onValidateFromLostUsername() {
//		String sessionId = requestGlobals.getHTTPServletRequest().getSession()
//				.getId();
//		if (!captchaService.isValidUserResponse(
//				retriever.getVerificationCode(), sessionId)) {
//			lostusername.recordError(verificationField,
//					"Verification code you entered does not match");
//		}
	}

	void onValidateFromLostusername() {
		try {
			EAAAccount acc = new EAAAccount(
					service.findAccountByEmail(retriever.getEmail()));

			
			
    		Notification notification = new Notification();
    		notification.setSubject(Messages.getString("subject.lostusername"));

    		HashMap<String, String> rep = new HashMap<String, String>();
    		rep.put("acc.getUid()", acc.getUid());

    		String body = Messages.replace(
    				Messages.getString("body.lostusername"), rep);
    		notification.setBody(body);

    		File file = new File(Messages.getString("mail.template.path"));
    		String layout = FileUtils.readFileToString(file);
    		notification.setLayout(layout);
    		EAAAccount mailAcc = new EAAAccount();
    		mailAcc.setEmail(retriever.getEmail());
    		notificationService.notify(notification, mailAcc);

		} catch (Exception e) {
			lostusername
					.recordError("Your account was not found. Please check the spelling of your email address");
		}
	}

	Object onSuccess() throws Exception {
		// find the email address

		return index;
	}
}
