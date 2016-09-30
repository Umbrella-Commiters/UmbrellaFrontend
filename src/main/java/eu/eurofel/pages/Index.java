package eu.eurofel.pages;

import javax.naming.NamingException;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

import eu.eurofel.entities.UserSession;

/**
 * Start page of application euu.
 */
@Secure
public class Index {

	public static final String SHIB = "SHIB";

	@SuppressWarnings("unused")
	@Property
	private boolean shib;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private Request _request;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

	void onPrepareForRender() throws NamingException {
		if (_request.getSession(true).getAttribute(Index.SHIB).equals(true)) {
			if (requestGlobals.getRequest().getHeader("Shib-Session-ID") != null) {
				_request.getSession(true).setAttribute(Index.SHIB, true);
				shib = true;
			} else {
				shib = false;
			}
		}
	}

	public boolean getLoggedin() {
		boolean found = false;

		// log the user out, if a session exists, but no EAAHash is present (the
		// user has logged out)
		if (userSession != null && userSession.isLoggedIn() && _request != null && _request.getSession(false) != null
				&& (_request.getHeader("EAAHash") == null || _request.getHeader("EAAHash").equals(""))) {
			_request.getSession(false).invalidate();
			userSession = null;
		}

		if (userSession != null && userSession.isLoggedIn()) {
			found = true;
		}

		return found;
	}

	public String getUsername() {

		if (userSession != null && userSession.isLoggedIn() && !userSession.getUserName().equals("")) {
			return userSession.getUserName();
		}
		return "";
	}

	public boolean getHasSession() {
		if (_request.getSession(true).getAttribute(Index.SHIB) == null) {
			return false;
		}
		return (_request.getSession(true).getAttribute(Index.SHIB).equals(true) ? true
				: false);
	}
}
