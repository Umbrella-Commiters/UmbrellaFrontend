package eu.eurofel.services;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;

@Service
public interface NotificationService {

	void notify(Notification notification, EAAAccount... account)
			throws Exception;

}
