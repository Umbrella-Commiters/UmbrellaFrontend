package eu.eurofel.pages.bridge.endpoint;

import java.io.UnsupportedEncodingException;

import javax.naming.NamingException;

import org.apache.commons.codec.binary.Base64;
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
public class BasicAuth extends BridgeEndpoint {

	@SessionState(create = true)
	private UserSession userSession;

	@Inject
	private Request _request;

	@Property
	private FederationBridge retriever;

	@InjectPage
	private BridgeSelector selector;

	public String getFederationName() {
		return "BasicAuth";
	}

	// on activation extract the remote user information to the local session.
	Object onActivate() throws NamingException, UnsupportedEncodingException {
		retriever = new FederationBridge();
		retriever.setFederationName(getFederationName());
		retriever.setFederationAuthMethod("Username/Password");
		String value = _request.getHeader("authorization").split(" ")[1];
		value = new String(Base64.decodeBase64(value), "UTF-8").split(":")[0];
		retriever.setFederationUID(value);
		userSession.setFederation(retriever);
		return selector;
	}

}
