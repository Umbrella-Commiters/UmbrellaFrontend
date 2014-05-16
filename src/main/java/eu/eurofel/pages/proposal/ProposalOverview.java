package eu.eurofel.pages.proposal;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ajax.MultiZoneUpdate;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;

import eu.eurofel.annotation.Private;
import eu.eurofel.base.Mode;
import eu.eurofel.entities.Proposal;
import eu.eurofel.entities.Section;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.ProposalService;
import eu.eurofel.util.ProposalStreamResponse;

@Private
@Secure
@Import(stylesheet = "ajaxcomponentscrud.css")
public class ProposalOverview {

	private ResourceBundle help = ResourceBundle.getBundle("help");

	@Persist
	private String helpText;
//
	@Property
	private UploadedFile file;

	@Property
	@Persist
	private boolean _highlightZoneUpdates;

	@SuppressWarnings("unused")
	@Property
	private String _highlightSectionId;

	@SuppressWarnings("unused")
	@Property
	private Mode _mode;

	@Property
	private String _sectionId;

	@SuppressWarnings("unused")
	@Property
	private String _helpEntry;

	@Property
	@SessionState
	private Proposal _proposal;

	@Inject
	private ProposalService proposalService;

	@Inject
	@Path("context:layout/images/create_proposal.png")
	private Asset _createProposalIcon;

	@Inject
	@Path("context:layout/images/create_section.png")
	private Asset _createSectionIcon;
	// Generally useful bits and pieces

	@InjectComponent
	private Zone _listZone;

	@InjectComponent
	private Zone _editorZone;

	@InjectComponent
	private Zone _createZone;

	@InjectComponent
	private Zone _deleteZone;

	@SuppressWarnings("unused")
	@InjectComponent
	private Zone _helpZone;

	@InjectComponent
	private Zone _uploadZone;

	@InjectComponent
	private Zone _titleZone;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;
	
	
	// The code

	// setupRender() is called by Tapestry right before it starts rendering the
	// page.

	void setupRender() {
		_highlightSectionId = _sectionId;
	}

	// /////////////////////////////////////////////////////////////////////
	// FILTER
	// /////////////////////////////////////////////////////////////////////

	// Handle event "filter" from component "list"
	Object onFilterFromList(String highlightSectionId) {
		_highlightSectionId = highlightSectionId;
		return _listZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// CREATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toCreate" from this page

	Object onToCreate() {
		// if no proposal exists, create one
		_proposal = new Proposal();
		_sectionId = null;
		_highlightSectionId = null;
		helpText = "proposal.create";
		return getListAndEditorZones();
	}

	Object onToCreateSection() {
		// if no proposal exists, create one
		_mode = Mode.CREATE;
		_sectionId = null;
		_highlightSectionId = null;
		helpText = "proposal.section.create";

		return getListAndEditorZones();
	}

	Object onToEditProposal() {
		// if no proposal exists, create one
		_mode = Mode.EDIT_PROPOSAL;
		_sectionId = null;
		_highlightSectionId = null;
		helpText = "proposal.section.create";
		return getListAndEditorZones();
	}

	// Handle event "cancelCreate" from component "editor"

	Object onCancelCreateFromEditor() {
		_mode = null;
		_sectionId = null;
		return _editorZone.getBody();
	}

	// Handle event "successfulCreate" from component "editor"

	Object onSuccessfulCreateFromEditor(String sectionId) {
		_mode = Mode.REVIEW;
		_sectionId = sectionId;
		_highlightSectionId = sectionId;
		return getListAndEditorZones();
	}

	// Handle event "failedCreate" from component "editor"

	Object onFailedCreateFromEditor() {
		_mode = Mode.CREATE;
		_sectionId = null;
		return _editorZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// File Upload
	// /////////////////////////////////////////////////////////////////////
	Object onSuccessFromUploadFile() {
		_proposal = proposalService
				.getProposalFromInputStream(file.getStream());
		return null;
	}

	// /////////////////////////////////////////////////////////////////////
	// REVIEW
	// /////////////////////////////////////////////////////////////////////

	// Handle event "selected" from component "list"

	Object onSelectedFromList(String sectionId) {
		_mode = Mode.REVIEW;
		_sectionId = sectionId;
		_highlightSectionId = sectionId;
		return getListAndEditorZones();
	}

	// /////////////////////////////////////////////////////////////////////
	// UPDATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toUpdate" from component "editor"

	Object onToUpdateFromEditor(String sectionId) {
		_mode = Mode.UPDATE;
		_sectionId = sectionId;
		helpText = "proposal.section.update";
		return _editorZone.getBody();
	}

	// Handle event "cancelUpdate" from component "editor"

	Object onCancelUpdateFromEditor(String sectionId) {
		_mode = Mode.REVIEW;
		_sectionId = sectionId;
		return _editorZone.getBody();
	}

	Object onCancelProposalUpdateFromEditor(Proposal proposal) {
		_mode = Mode.REVIEW;
		_sectionId = null;
		return _editorZone.getBody();
	}

	// Handle event "successfulUpdate" from component "editor"

	Object onSuccessfulUpdateFromEditor(String sectionId) {
		_mode = Mode.REVIEW;
		_sectionId = sectionId;
		_highlightSectionId = sectionId;
		return getListAndEditorZones();
	}

	Object onSuccessfulProposalUpdateFromEditor(Proposal proposal) {
		_mode = Mode.REVIEW;
		_sectionId = null;
		_highlightSectionId = null;
		return getListAndEditorZones();
	}

	// Handle event "failedUpdate" from component "editor"

	Object onFailedUpdateFromEditor(String sectionId) {
		return _editorZone.getBody();
	}

	Object onFailedProposalUpdateFromEditor(Proposal proposal) {
		return _editorZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// DOWNLOAD
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toDelete" from this page

	StreamResponse onActionFromDownload() {
		return new ProposalStreamResponse(_proposal, proposalService);
	}

	// /////////////////////////////////////////////////////////////////////
	// DELETE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toDelete" from this page

	Object onToDelete() {
		_proposal = null;
		_sectionId = null;
		_highlightSectionId = null;
		return getListAndEditorZones();
	}

	Object onToSubmit() {
		// if no proposal exists, create one
		_mode = Mode.SUBMIT;
		_sectionId = null;
		_highlightSectionId = null;
		return getListAndEditorZones();
	}

	// Handle event "successfulDelete" from component "editor"

	Object onSuccessfulDeleteFromEditor(String sectionId) {
		_mode = null;
		_sectionId = null;
		_highlightSectionId = null;
		return getListAndEditorZones();
	}

	// Handle event "failedDelete" from component "editor"

	Object onFailedDeleteFromEditor(String sectionId) {
		_mode = Mode.REVIEW;
		_sectionId = sectionId;
		return _editorZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// GETTERS
	// /////////////////////////////////////////////////////////////////////

	private MultiZoneUpdate getListAndEditorZones() {
		return new MultiZoneUpdate("listZone", _listZone.getBody())
				.add("editorZone", _editorZone.getBody())
				.add("createZone", _createZone.getBody())
				.add("deleteZone", _deleteZone.getBody())
				// .add("helpZone", _helpZone.getBody())
				.add("uploadZone", _uploadZone.getBody())
				.add("titleZone", _titleZone.getBody());
	}

	public String getZoneUpdateFunction() {
		return _highlightZoneUpdates ? "highlight" : "show";
	}

	public Asset getCreateProposalIcon() {
		return _createProposalIcon;
	}

	public Asset getCreateSectionIcon() {
		return _createSectionIcon;
	}

	public boolean getProposalFull() {
		if (_proposal.getSections().size() >= Section.values().length) {
			return true;
		}
		return false;
	}

	public String getHiddenCSSStyle() {
		if (_proposal == null) {
			return "visibility: hidden;";
		} else {
			return "";
		}
	}

	public List<String> getHelpEntries() {
		if (helpText != null) {
			ArrayList<String> helpEntries = new ArrayList<String>();
			String[] ht = help.getString(helpText).split("\\.");
			for (String helpEntry : ht) {
				helpEntries.add(helpEntry);
			}
			return helpEntries;

		}
		return null;
	}
}
