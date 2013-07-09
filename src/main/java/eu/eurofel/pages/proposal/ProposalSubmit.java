package eu.eurofel.pages.proposal;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.Proposal;
import eu.eurofel.entities.UserSession;

@Private
@Secure
public class ProposalSubmit {

	@SuppressWarnings("unused")
	@Property
	@SessionState
	private Proposal _proposal;

	@SuppressWarnings("unused")
	@Property
	private eu.eurofel.entities.ProposalSection _sec;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;
}
