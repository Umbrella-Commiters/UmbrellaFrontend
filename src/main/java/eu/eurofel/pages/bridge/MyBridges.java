package eu.eurofel.pages.bridge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.BridgeFederation;
import eu.eurofel.entities.FieldCopy;
import eu.eurofel.services.EAAService;

@Private
@Secure
public class MyBridges {

	@Property
	private List<BridgeFederation> bridges;

	private BridgeFederation bridge;

	@Inject
	private EAAService service;

	@Inject
	private Request _request;

	@Component(id = "deletables")
	private Form form;

	@InjectComponent
	private Checkbox delete;

	private List<String> personsToDelete;

	private boolean inFormSubmission;

	private List<BridgeFederation> personsSubmitted;

	private int rowNum;
	private Map<Integer, FieldCopy> deleteCopyByRowNum;

	public BridgeFederation getBridge() {
		return bridge;
	}

	public void setBridge(BridgeFederation bridge) {
		this.bridge = bridge;

		if (inFormSubmission) {
			personsSubmitted.add(bridge);
		}
	}

	void setupRender() throws NamingException {
		bridges = new ArrayList<BridgeFederation>();
		if (_request.getHeader("EAAHash") != null) {
			NamingEnumeration<?> attrs = service.findBridges(_request
					.getHeader("EAAHash"));

			if (attrs != null) {
				while (attrs.hasMore()) {
					BridgeFederation bf = new BridgeFederation();
					Object tmp = attrs.next();
					SearchResult sr = (SearchResult) tmp;
					Attributes at = sr.getAttributes();
					bf.setBridgeFederationSrc(at.get("BridgeFederationSrc")
							.get().toString());
					bf.setBridgeFederationUid(at.get("BridgeFederationUID")
							.get().toString().replaceAll("\\!", " "));
					bf.setBridgeUmbrellaUid(at
							.get("BridgeFederationUmbrellaUID").get()
							.toString());
					bf.setBridgeFederationUmbrellaUsername(at
							.get("BridgeFederationUmbrellaUsername").get()
							.toString());
					bridges.add(bf);
				}
			}

		}
	}

	// The Loop component will automatically call this for every row as it is
	// rendered.
	public boolean isDelete() {
		return false;
	}

	// The Loop component will automatically call this for every row on submit.
	public void setDelete(boolean delete) {
		rowNum++;
		deleteCopyByRowNum.put(rowNum, new FieldCopy(this.delete));

		if (inFormSubmission) {
			if (delete) {
				// Put the current person in our list of ones to delete. Record
				// their id but not version - we shouldn't
				// assume person.version has been overwritten yet with the
				// submitted value - it may still hold the
				// database value.
				personsToDelete.add(bridge.getUid());
			}
		}

	}

	void onValidateFromDeletables() {

		if (form.getHasErrors()) {
			// We get here only if a server-side validator detected an error.
			return;
		}

		// Error if any person to delete has a null id - it means toValue(...)
		// found they are no longer in the database.

		for (String personToDelete : personsToDelete) {
			if (personToDelete == null) {
				form.recordError("The list of persons is out of date. Please refresh and try again.");
				return;
			}
		}

		// Populate our list of persons to delete with the submitted versions
		// (see setDelete(...) for more).
		// Also, simulate a server-side validation error: return error if
		// deleting a person with first name BAD_NAME.

		// for (String personToDelete : personsToDelete) {
		// rowNum = 0;
		//
		// for (BridgeFederation p : personsSubmitted) {
		// rowNum++;
		//
		// // if (p.getUid() != null && p.getUid().equals(personToDelete))
		// // {
		// // personToDelete.setVersion(p.getVersion());
		// //
		// // if (p.getFirstName() != null &&
		// // p.getFirstName().equals(BAD_NAME)) {
		// // // Unfortunately, at this point the field "delete" is from
		// // the final row of the Loop.
		// // // Fortunately, we have a copy of the correct field, so we
		// // can record the error with that.
		// //
		// // Field field = deleteCopyByRowNum.get(rowNum);
		// // form.recordError(field, "Cannot delete " + BAD_NAME + ".");
		// // return;
		// // }
		// //
		// // break;
		// // }
		// }
		// }

		try {
			System.out.println(">>> personsSubmitted = " + personsSubmitted);
			System.out.println(">>> personsToDelete = " + personsToDelete);
			// In a real application we would persist them to the database
			// instead of printing them
			// personManagerService.bulkEditPersons(new ArrayList<Person>(), new
			// ArrayList<Person>(), personsToDelete);

			// now we should delete these entries from the DB
			for (String uid : personsToDelete) {
				if (service.removeBridge(uid)) {
				} else {
					form.recordError("Could not unmatch: " + uid);
				}
			}
		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			form.recordError(e.getLocalizedMessage());
		}
	}

}
