package eu.eurofel.services;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import eu.eurofel.Messages;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.services.impl.EmailNotificationService;

public class Test {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		EmailNotificationService notificationService = new EmailNotificationService();
		EAAAccount eAAAccount = new EAAAccount();
		eAAAccount.setEaahash(UUID.randomUUID().toString());
		eAAAccount.setUid("bjoern");
		eAAAccount.setEmail("bjoern.abt@psi.ch");
		eAAAccount.setTarget("yourself");

		String activationURL = Messages.getString("eaa.url") + "euu/validate?uid="
				+ eAAAccount.getUid() + "&uuid=" + eAAAccount.getEaahash();
		if (eAAAccount.getTarget() != null
				&& !eAAAccount.getTarget().equals("")) {
			activationURL = activationURL + "&target=" + eAAAccount.getTarget();
		}
		
		Notification notification = new Notification();
		notification.setSubject(Messages.getString("subject.createaccount"));
		
		HashMap<String, String> rep = new HashMap<String, String>();
		rep.put("eAAAccount.getUid()", eAAAccount.getUid());
		rep.put("activationURL", activationURL);
		
		String body = Messages.replace(Messages.getString("body.createaccount"), rep);
		notification.setBody(body);

		File file = new File("C:/Users/abt_b/git/UmbrellaFrontend-Redesign/src/main/resources/umbrellaMailTemplate.html");
		String layout = FileUtils.readFileToString(file);
		notification.setLayout(layout);
		notificationService.notify(notification, eAAAccount);
	}

}
