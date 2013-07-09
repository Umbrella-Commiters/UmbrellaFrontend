package eu.eurofel.services;

import java.io.IOException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.UserSession;
import eu.eurofel.entities.UserSessionImpl;

public class AccessController implements Dispatcher {
	private final static String LOGIN_PAGE = "/login";
	// private final static String INDEX_PAGE = "/index";

	private ApplicationStateManager asm;
	@SuppressWarnings("unused")
	private UserSession newUserSession;
	private final ComponentClassResolver resolver;
	private final ComponentSource componentSource;

	@Inject
	private Request _request;

	/**
	 * Receive all the services needed as constructor arguments. When we bind
	 * this service, T5 IoC will provide all the services !
	 */
	public AccessController(ApplicationStateManager asm, ComponentClassResolver resolver, ComponentSource componentSource, UserSession newUserSession) {
		this.asm = asm;
		this.newUserSession = newUserSession;
		this.resolver = resolver;
		this.componentSource = componentSource;
	}

	public boolean dispatch(Request request, Response response) throws IOException {
		/*
		 * We need to get the Tapestry page requested by the user. So we parse
		 * the path extracted from the request
		 */
		String path = request.getPath();
		if (path.equals(""))
			return false;

		int nextslashx = path.length();
		String pageName;

		while (true) {
			pageName = path.substring(1, nextslashx);
			if (!pageName.endsWith("/") && resolver.isPageName(pageName))
				break;
			nextslashx = path.lastIndexOf('/', nextslashx - 1);
			if (nextslashx <= 1)
				return false;
		}
		return checkAccess(pageName, request, response);
	}

	/**
	 * Check the rights of the user for the page requested
	 * 
	 * @throws IOException
	 */
	public boolean checkAccess(String pageName, Request request, Response response) throws IOException {

		if (_request != null && _request.getHeader("EAAHash") != null && !_request.getHeader("EAAHash").equals("")) {
			if (!asm.exists(UserSession.class)) {
				asm.set(UserSession.class, new UserSessionImpl());
				UserSession userSession = asm.get(UserSession.class);
				userSession.setLoggedIn(true);
				userSession.setUserName(_request.getHeader("uid"));
			}
		}

		boolean canAccess = true;

		/* Is the requested page private ? */
		Component page = componentSource.getPage(pageName);
		boolean privatePage = page.getClass().getAnnotation(Private.class) != null;
		if (privatePage) {
			canAccess = false;
			/* Is the user already authentified ? */
			if (asm.exists(UserSession.class)) {
				UserSession userSession = asm.get(UserSession.class);
				canAccess = userSession.isLoggedIn();
			}
		}

		/*
		 * This page can't be requested by a non authentified user => we
		 * redirect him on the signon page
		 */
		if (!canAccess) {
			response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
			return true; // Make sure to leave the chain
		}

		return false;
	}
}
