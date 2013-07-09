package eu.eurofel.components;

import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.eurofel.entities.Proposal;

@Events({ ProposalList.SELECTED })
public class ProposalList {
	public static final String SELECTED = "selected";

	// Parameters

	@Parameter
	@Property
	private String _highlightSectionId;

	@SuppressWarnings("unused")
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String _zone;

	// Screen fields

	@Property
	private List<eu.eurofel.entities.ProposalSection> _sections;

	@Property
	private eu.eurofel.entities.ProposalSection _sec;

	@Property
	@Parameter
	private Proposal _proposal;

	@SuppressWarnings("unused")
	@Component(id = "list")
	@Property
	private Grid _list;

	@Inject
	@Path("context:layout/images/create_facility.png")
	private Asset _createSectionIcon;

	// Generally useful bits and pieces

	void setupRender() {
		if (_proposal != null) {
			_sections = _proposal.getSections();
			if (_sections != null) {
				Collections.sort(_sections);
				Collections.reverse(_sections);
			}
		}
		if (_highlightSectionId == null) {
			// Null is a nuisance because it fails to match the signature of our
			// onSuccess method.
			_highlightSectionId = "";
		}
	}

	boolean onSelected(String personId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	public String getLinkCSSClass() {
		if (_sec != null
				&& new String(_sec.getId()).equals(_highlightSectionId)) {
			return "active";
		} else {
			return "";
		}
	}

	public Asset getCreateSectionIcon() {
		return _createSectionIcon;
	}

}
