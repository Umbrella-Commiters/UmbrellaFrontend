package eu.eurofel.components;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.eurofel.base.Mode;
import eu.eurofel.entities.Proposal;
import eu.eurofel.entities.Section;
import eu.eurofel.web.commons.GenericSelectModel;

//@Import(library = { "context:layout/eq_config.js",
//		"context:layout/eq_editor-lite-9.js" }, stylesheet = { "context:layout/equation-embed.css" })
@Events({ ProposalEditor.CANCEL_CREATE, ProposalEditor.SUCCESSFUL_CREATE,
		ProposalEditor.FAILED_CREATE, ProposalEditor.TO_UPDATE,
		ProposalEditor.CANCEL_UPDATE, ProposalEditor.SUCCESSFUL_UPDATE,
		ProposalEditor.FAILED_UPDATE, ProposalEditor.SUCCESFUL_DELETE,
		ProposalEditor.FAILED_DELETE,
		ProposalEditor.SUCCESSFUL_PROPOSAL_UPDATE,
		ProposalEditor.CANCEL_PROPOSAL_UPDATE,
		ProposalEditor.FAILED_PROPOSAL_UPDATE })
public class ProposalEditor {

	public static final String CANCEL_CREATE = "cancelCreate";
	public static final String SUCCESSFUL_CREATE = "successfulCreate";
	public static final String FAILED_CREATE = "failedCreate";
	public static final String TO_UPDATE = "toUpdate";
	public static final String CANCEL_UPDATE = "cancelUpdate";
	public static final String SUCCESSFUL_UPDATE = "successfulUpdate";
	public static final String FAILED_UPDATE = "failedUpdate";
	public static final String CANCEL_PROPOSAL_UPDATE = "cancelProposalUpdate";
	public static final String SUCCESSFUL_PROPOSAL_UPDATE = "successfulProposalUpdate";
	public static final String FAILED_PROPOSAL_UPDATE = "failedProposalUpdate";
	public static final String SUCCESFUL_DELETE = "successfulDelete";
	public static final String FAILED_DELETE = "failedDelete";

	// Parameters

	@Parameter
	@Property
	private Mode _mode;

	@Parameter
	@Property
	private String _sectionId;

	@SuppressWarnings("unused")
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String _zone;

	// Screen fields

	@Property
	private eu.eurofel.entities.ProposalSection _section;

	@SuppressWarnings("unused")
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String _deleteMessage;

	// Generally useful bits and pieces

	@Component(id = "createForm")
	private Form _createForm;

	@Component(id = "updateForm")
	private Form _updateForm;

	@Inject
	private ComponentResources _componentResources;

	@SuppressWarnings("unused")
	@Inject
	private Messages _messages;

	private Object _zonesToUpdate;

	@Inject
	@Path("context:layout/images/delete_section.png")
	private Asset _deleteSectionIcon;

	@Inject
	@Path("context:layout/images/update_section.png")
	private Asset _updateSectionIcon;

	@Inject
	@Path("context:layout/images/cancel_icon.png")
	private Asset _cancelIcon;

	@Inject
	@Path("context:layout/images/save_icon.png")
	private Asset _saveIcon;

	@Property
	@Parameter
	// Persist the list to support inplace="true" on the Grid
	private Proposal _proposal;

	private GenericSelectModel<Section> _beans;

	// add editors
//	@SuppressWarnings("unused")
//	@Component(parameters = { "value=section.text", "height=450px",
//			"width=600px" })
//	private Editor text;
//
//	@SuppressWarnings("unused")
//	@Component(parameters = { "value=section.text", "height=450px",
//			"width=600px" })
//	private Editor updtext;

	//
	// @SuppressWarnings("unused")
	// @Component(parameters = { "value=section.text",
	// "customConfiguration=asset:../assets/js/myEditorConfig.js",
	// "toolbarSet=MyToolbar" })
	// private Editor updateText;

	//
	//
	// @Property
	// @Persist
	// private String _updateText;
	//
	// @Property
	// private String _crtText;

	// The code

	// setupRender() is called by Tapestry right before it starts rendering the
	// component.

	void setupRender() {

		if (_sectionId == null) {
			_section = null;
		} else {
			if (_section == null && _proposal != null) {
				_section = _proposal.getSectionById(_sectionId);
				// _updateText = _section.getText();
				// Handle null person in the template.
			}
		}

	}

	// /////////////////////////////////////////////////////////////////////
	// CREATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "cancelCreate"

	boolean onCancelCreate() {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Component "createForm" triggers the PREPARE event when it is rendered or
	// submitted

	void onPrepareFromCreateForm() throws Exception {
		// Instantiate a Person for the form data to overlay.
		_section = new eu.eurofel.entities.ProposalSection();
		ArrayList<Section> list = new ArrayList<Section>();
		if (_proposal != null) {
			for (Section section : Section.values()) {
				if (!_proposal.containsSection(section)) {
					list.add(section);
				}
			}
		}
		_beans = new GenericSelectModel<Section>(list);
	}

	// Component "createForm" triggers the VALIDATE_FORM event when it is
	// submitted

	void onValidateFormFromCreateForm() {

		if (_createForm.getHasErrors()) {
			// We get here only if a validator detected an error and javascript
			// is disabled in the browser.
			return;
		}

		try {
			// System.out.println("CREATE TEXT: " + _crtText);
			System.out.println("Section Text: " + _section.getText());
			// _section.setText(_createText);
			_proposal.getSections().add(_section);
		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			_createForm.recordError(e.getLocalizedMessage());
		}
	}

	// Component "createForm" triggers SUCCESS or FAILURE when it is submitted,
	// depending on whether VALIDATE_FORM
	// records an error

	Object onSuccessFromCreateForm() {
		// We want to tell our containing page explicitly what person we've
		// created, so we trigger new event
		// "successfulCreate" with a parameter. It will bubble up because we
		// don't have a handler method for it.
		// When the event has been handled, Tapestry will call my callback with
		// the result.
		_componentResources.triggerEvent(SUCCESSFUL_CREATE,
				new Object[] { _section.getId() }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	Object onFailureFromCreateForm() {
		// Rather than letting "failure" bubble up which doesn't say what you
		// were trying to do, we trigger new event
		// "failedCreate". It will bubble up because we don't have a handler
		// method for it.
		// When the event has been handled, Tapestry will call my callback with
		// the result.
		_componentResources.triggerEvent(FAILED_CREATE, null, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	// /////////////////////////////////////////////////////////////////////
	// REVIEW
	// /////////////////////////////////////////////////////////////////////

	// /////////////////////////////////////////////////////////////////////
	// UPDATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toUpdate"

	boolean onToUpdate(String sectionId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Handle event "cancelUpdate"

	boolean onCancelUpdate(String sectionId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Component "updateForm" triggers the PREPARE event when it is rendered or
	// submitted

	void onPrepareFromUpdateForm(String sectionId) {
		_sectionId = sectionId;
		if (_section == null) {
			// Get objects for the form fields to overlay.
			_section = _proposal.getSectionById(_sectionId);
			// Handle null person in the template.
		}
	}

	// Component "updateForm" triggers the VALIDATE_FORM event when it is
	// submitted

	void onValidateFormFromUpdateForm() {

		if (_updateForm.getHasErrors()) {
			// We get here only if a validator detected an error and javascript
			// is disabled in the browser.
			return;
		}

		try {

		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			_updateForm.recordError(e.getLocalizedMessage());
		}
	}

	// Component "updateForm" triggers SUCCESS or FAILURE when it is submitted,
	// depending on whether VALIDATE_FORM
	// records an error

	// Component "updateForm" triggers SUCCESS or FAILURE when it is submitted,
	// depending on whether VALIDATE_FORM
	// records an error

	Object onSuccessFromUpdateForm() {
		// We want to tell our containing page explicitly what person we've
		// updated, so we trigger new event
		// "successfulUpdate" with a parameter. It will bubble up because we
		// don't have a handler method for it.
		// When the event has been handled, Tapestry will call my callback with
		// the result.
		_componentResources.triggerEvent(SUCCESSFUL_UPDATE,
				new Object[] { _sectionId }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	Object onFailureFromUpdateForm() {
		// Rather than letting "failure" bubble up which doesn't say what you
		// were trying to do, we trigger new event
		// "failedUpdate". It will bubble up because we don't have a handler
		// method for it.
		// When the event has been handled, Tapestry will call my callback with
		// the result.
		_componentResources.triggerEvent(FAILED_UPDATE,
				new Object[] { _sectionId }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	// /////////////////////////////////////////////////////////////////////
	// UPDATE PROPOSAL
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toProposalUpdate"

	boolean onToProposalUpdate(String sectionId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Handle event "cancelProposalUpdate"

	boolean onCancelProposalUpdate(String sectionId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Component "updateForm" triggers the VALIDATE_FORM event when it is
	// submitted

	void onValidateFormFromUpdateProposalForm() {

		if (_updateForm.getHasErrors()) {
			// We get here only if a validator detected an error and javascript
			// is disabled in the browser.
			return;
		}

		try {

		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			_updateForm.recordError(e.getLocalizedMessage());
		}
	}

	Object onSuccessFromUpdateProposalForm() {
		// We want to tell our containing page explicitly what person we've
		// updated, so we trigger new event
		// "successfulUpdate" with a parameter. It will bubble up because we
		// don't have a handler method for it.
		// When the event has been handled, Tapestry will call my callback with
		// the result.
		_componentResources.triggerEvent(SUCCESSFUL_PROPOSAL_UPDATE,
				new Object[] { _proposal }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	Object onFailureFromUpdateProposalForm() {
		// Rather than letting "failure" bubble up which doesn't say what you
		// were trying to do, we trigger new event
		// "failedUpdate". It will bubble up because we don't have a handler
		// method for it.
		// When the event has been handled, Tapestry will call my callback with
		// the result.
		_componentResources.triggerEvent(FAILED_PROPOSAL_UPDATE,
				new Object[] { _proposal }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	// /////////////////////////////////////////////////////////////////////
	// DELETE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "delete"

	Object onDelete(String sectionId) {

		// _facility = _daoService.getFacilityDao().selectFacilityById(
		// facilityId.toString());
		// Handle null person in the template.
		boolean successfulDelete = false;
		System.out.println("DELETING");

		// Delete the person from the database unless they've been
		// modified elsewhere

		try {
			_proposal.removeSection(sectionId);
			successfulDelete = true;

		} catch (Exception e) {
			// Display the cause. In a real system we would try harder
			// to get a user-friendly message.
			_deleteMessage = e.getLocalizedMessage();
		}

		if (successfulDelete) {
			// Trigger new event "successfulDelete" (which in this example will
			// bubble up to the page).
			// When the event has been handled, Tapestry will call my callback
			// with the result.
			_componentResources.triggerEvent(SUCCESFUL_DELETE,
					new Object[] { _sectionId }, getMyCallback());
		} else {
			// Trigger new event "failedDelete" (which in this example will
			// bubble up to the page).
			// When the event has been handled, Tapestry will call my callback
			// with the result.
			_componentResources.triggerEvent(FAILED_DELETE,
					new Object[] { _sectionId }, getMyCallback());
		}

		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	// /////////////////////////////////////////////////////////////////////
	// OTHER
	// /////////////////////////////////////////////////////////////////////

	// Getters

	public boolean isModeCreate() {
		return _mode == Mode.CREATE;
	}

	public boolean isModeReview() {
		return _mode == Mode.REVIEW;
	}

	public boolean isModeUpdate() {
		return _mode == Mode.UPDATE;
	}

	public boolean isModeSubmit() {
		return _mode == Mode.SUBMIT;
	}

	public boolean isModeEditProposal() {
		return _mode == Mode.EDIT_PROPOSAL;
	}

	public boolean isModeNull() {
		return _mode == null;
	}

	public String getDatePattern() {
		return "dd/MM/yyyy";
	}

	public Format getDateFormat() {
		return new SimpleDateFormat(getDatePattern());
	}

	// When you trigger an event you can also specify a callback. When the event
	// has been handled Tapestry will call the
	// callback with the result. This component expects the event handlers will
	// return the zone or zones to update.

	private ComponentEventCallback<Object> getMyCallback() {

		ComponentEventCallback<Object> callback = new ComponentEventCallback<Object>() {
			public boolean handleResult(Object result) {
				_zonesToUpdate = result;
				return false;
			}
		};

		return callback;
	}

	public Asset getDeleteSectionIcon() {
		return _deleteSectionIcon;
	}

	public Asset getUpdateSectionIcon() {
		return _updateSectionIcon;
	}

	public Asset getCancelIcon() {
		return _cancelIcon;
	}

	public Asset getSaveIcon() {
		return _saveIcon;
	}

	public Date getPingTime() {
		return new Date();
	}

	public GenericSelectModel<Section> getBeans() {
		return _beans;
	}

}
