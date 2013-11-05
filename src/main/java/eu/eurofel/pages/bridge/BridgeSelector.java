package eu.eurofel.pages.bridge;

import javax.naming.NamingException;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;

import eu.eurofel.entities.FederationBridge;
import eu.eurofel.entities.UserSession;

@Secure
public class BridgeSelector {

	@SessionState(create = false)
	private UserSession userSession;

	@Property
	private FederationBridge retriever;

	void onActivate() throws NamingException {
		if (retriever == null) {
			retriever = new FederationBridge();
		}
		if (userSession.getFederation() != null) {
			retriever = userSession.getFederation();
		}
	}

}
