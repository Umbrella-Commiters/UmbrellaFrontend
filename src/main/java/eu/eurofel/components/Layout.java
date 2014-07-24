package eu.eurofel.components;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestGlobals;

import eu.eurofel.Messages;
import eu.eurofel.entities.UserSession;

/**
 * Layout component for pages of application euu.
 */
// @Import(stylesheet = "${stylesheetPath}")
// @IncludeJavaScriptLibrary(value = { "context:layout/js/jquery.easing.js",
// "context:layout/js/jquery.foswiki.js",
// "context:layout/js/jquery.metadata.js",
// "context:layout/js/jquery.min.js", "context:layout/js/psiweb.js" })
@Import(stylesheet = "context:layout/css/ef_misc.css")
public class Layout {
	@Inject
	private Block _psilayout;

	@Inject
	private Block _desylayout;

	@SuppressWarnings("unused")
	@Inject
	private Block _normlayout;

	@SuppressWarnings("unused")
	@Inject
	private Block _html5layout;

	@Inject
	private Block _stripped;
	
	@Inject
	private Block _layout;
	/** The page title, for the <title> element and the <h1>element. */
	@SuppressWarnings("unused")
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@Property
	private String pageName;

	@SuppressWarnings("unused")
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String sidebarTitle;

	@SuppressWarnings("unused")
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Block sidebar;

	@Inject
	@Path("context:layout/images/logo_desy.gif")
	private Asset _desyIcon;

	@Inject
	@Path("context:layout/images/logo_psi.jpg")
	private Asset _psiIcon;

	@Inject
	@Path("context:layout/images/spacer.gif")
	private Asset _eaaIcon;

	@Inject
	private Request _request;

	@Inject
	private ComponentResources resources;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private RequestGlobals requestGlobals;

	//
	// @Inject
	// private RequestGlobals requestGlobals;

	public String getClassForPageName() {
		return resources.getPageName().equalsIgnoreCase(pageName) ? "current_page_item" : null;
	}

	public String getPageNameFull() {
		String[] tmpNames = pageName.split("\\/");

		if (tmpNames.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = tmpNames.length - 1; i >= 0; i--) {
				sb.append(tmpNames[i]);
				if (i != 0) {
					sb.append(" ");
				}
			}
			String tmpName = sb.toString();
			return toUpperCase(tmpName);
		}
		return toUpperCase(pageName);
	}

	private String toUpperCase(String name) {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public String[] getPageNames() {
		StringBuffer sb = new StringBuffer();
		if (userSession != null && userSession.isLoggedIn()) {
            sb.append("account/changepassword|Update Account,");
			sb.append("account/update|Update Account at User Offices,");
		} else {
			sb.append("account/create|Create Account,");
			sb.append("Contact|Contact,");
			sb.append("login|Login");
		}
		return sb.toString().split(",");
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
		// // Log out user if session with umbrella is gone
		// if (_request.getSession(true).getAttribute(Index.SHIB).equals(false))
		// {
		//
		// requestGlobals.getRequest().getSession(false).invalidate();
		// userSession = null;
		// }

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

	public Object getLayout() {
		String serverName = _request.getServerName();
		String stripped = _request.getParameter("stripped");
		if(stripped != null && stripped.equals("true")){
			return _stripped;
		}
		return _layout;
	}

	public String getEaahash() {
		return _request.getHeader("EAAHash");
	}

	public String getUid() {
		if (_request.getHeader("uid") == null) {
			return "No EAA-Session";
		}
		return _request.getHeader("uid");
	}

	// public String getStylesheet() {
	// String serverName = _request.getServerName();
	// if (serverName.endsWith("desy.de")) {
	// return "context:layout/layout.css";
	// } else if (serverName.endsWith("psi.ch")) {
	// return "context:layout/layout.css";
	// }
	// return "context:layout/layout.css";
	// }

	public String getStylesheetPath() {
		String template = "http://eaa.desy.de:8080/euu/layout/@@@layout.css";
		String serverName = _request.getServerName();
		if (serverName.endsWith("desy.de")) {
			return template.replace("@@@", "desy");
		} else if (serverName.endsWith("psi.ch")) {
			return template.replace("@@@", "psi");
		}
		return template.replace("@@@", "");
	}

	public Asset getFacilityIcon() {
		String serverName = _request.getServerName();
		if (serverName.endsWith("desy.de")) {
			return _desyIcon;
		} else if (serverName.endsWith("psi.ch")) {
			return _psiIcon;
		}
		return _eaaIcon;
	}

	public String getPageURL() {
		return pageName.split("\\|")[0];
	}

	public String getPageFullName() {
		return pageName.split("\\|")[1];
	}

	public Object onActionFromLogout() throws MalformedURLException {
		requestGlobals.getHTTPServletRequest().getSession().invalidate();
		URL url = new URL(Messages.getString( "eaa.url" ) + "idp/Logout");
		return url;
	}
}
