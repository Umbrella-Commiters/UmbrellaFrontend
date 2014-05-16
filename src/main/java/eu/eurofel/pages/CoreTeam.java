package eu.eurofel.pages;

import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;

import eu.eurofel.entities.UserSession;

@Secure
public class CoreTeam {

	@SessionState(create = false)
	private UserSession userSession;

}
