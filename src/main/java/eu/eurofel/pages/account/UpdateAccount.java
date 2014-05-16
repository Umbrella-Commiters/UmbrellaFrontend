package eu.eurofel.pages.account;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.Address;
import eu.eurofel.entities.Affiliation;
import eu.eurofel.entities.CountryNames;
import eu.eurofel.entities.UserSession;
import eu.eurofel.pages.Index;
import eu.eurofel.services.AffiliationService;
import eu.eurofel.services.DisseminationService;
import eu.eurofel.services.EAAService;
import eu.eurofel.util.EAAHash;

@Private
@Secure
public class UpdateAccount {

	@SuppressWarnings("unused")
	@Property
	@Persist
	private String eaahash;

	@SuppressWarnings("unused")
	@Property
	@Persist
	private String eaakey;

	@InjectPage
	private Index index;

	@Inject
	private EAAService service;

	@Inject
	private Request _request;

	@Inject
	private AffiliationService aservice;

	@Inject
	private DisseminationService dservice;

	@Property
	private Address address = new Address();

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;
	// Screen fields

	// Generally useful bits and pieces

	@Inject
	private CountryNames _countryNames;

	// The code

	void onActivate() throws NamingException {
		address = new Address();
		address.setEaahash(_request.getHeader("EAAHash"));
		address.setEaakey(_request.getHeader("EAAKey"));
		eaahash = _request.getHeader("EAAHash");
		eaakey = _request.getHeader("EAAKey");

	}

	List<String> onProvideCompletionsFromNationality(String partial) {
		List<String> matches = new ArrayList<String>();
		partial = partial.toUpperCase();

		for (String countryName : _countryNames.getSet()) {
			if (countryName.startsWith(partial)) {
				matches.add(countryName);
			}
		}

		return matches;
	}

	
	JSONObject onAjaxValidateFromEmail() {

		String email = _request.getParameter("param");
		try {
			service.hasAccountEmail(_request.getHeader("EAAHash"),
					EAAHash.getSHA1Hash(email));
		} catch (Exception e) {
			return new JSONObject().put("error", e.getMessage());
		}

		return new JSONObject();
	}

	List<String> onProvideCompletionsFromAffiliation(String partial) {
		List<String> matches = new ArrayList<String>();
		partial = partial.toUpperCase();

		for (Affiliation affl : (List<Affiliation>) aservice.getAffiliations()) {
			if (affl.toString().toLowerCase().contains(partial.toLowerCase())) {
				matches.add(affl.toString());
			}
		}

		return matches;
	}

	Object onSuccess() throws NamingException {
		// Disseminate information
		if (address.getEaahash() != null || !address.getEaahash().equals("")) {
			dservice.disseminateInformation(address);
			address = new Address();
		}
		return index;
	}
}
