package eu.eurofel.pages.bridge.endpoint;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import eu.eurofel.entities.FederationBridge;
import eu.eurofel.entities.UserSession;
import eu.eurofel.pages.bridge.BridgeSelector;

@Secure
public class EduGain extends BridgeEndpoint {

	@SessionState(create = true)
	private UserSession userSession;

	@Inject
	private Request _request;

	@Property
	private FederationBridge retriever;

	@InjectPage
	private BridgeSelector selector;

	public String getFederationName() {
		return "eduGain";
	}

	// on activation extract the remote user information to the local session.
	Object onActivate() throws NamingException, UnsupportedEncodingException {

		// Just extract the info when not logged in via Umbrella
		if (!_request.getHeader("Shib-Identity-Provider").equals("https://umbrella.psi.ch/idp/shibboleth") && _request.getHeader("Shib-Identity-Provider") != null && !_request.getHeader("Shib-Identity-Provider").equals("")) {
			retriever = new FederationBridge();
			retriever.setFederationName(getFederationName());
			retriever.setFederationAuthMethod("Username/Password");

			for (String name : _request.getHeaderNames()) {
				System.out.println(name + ": " + _request.getHeader(name));
			}
			retriever.setFederationUID(_request.getHeader("persistent-id"));
			userSession.setFederation(retriever);
		}
		return selector;
	}
}
