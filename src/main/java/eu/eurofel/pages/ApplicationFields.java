package eu.eurofel.pages;

import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;

import eu.eurofel.entities.UserSession;

@Secure
public class ApplicationFields {

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;
}
