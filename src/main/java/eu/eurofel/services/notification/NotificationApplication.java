package eu.eurofel.services.notification;

import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.services.NotificationService;

public final class NotificationApplication {

	private NotificationService service;

	private NotificationApplication() {
	}

	private static class NotificationApplicationHolder {
		public static final NotificationApplication INSTANCE = new NotificationApplication();
	}

	public static NotificationApplication getInstance() {
		return NotificationApplicationHolder.INSTANCE;
	}

	public void notify(NotificationType type, Notification notification,
			EAAAccount... accounts) throws Exception {
		switch (type) {
		case MAIL:

			break;

		case SMS:

			break;

		default:
			break;
		}

		service.notify(notification, accounts);
	}
}
