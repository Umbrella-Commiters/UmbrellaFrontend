package eu.eurofel.pages.account;

import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;

import eu.eurofel.entities.UserSession;

@Secure
public class NotactivatedAccount {

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

}
