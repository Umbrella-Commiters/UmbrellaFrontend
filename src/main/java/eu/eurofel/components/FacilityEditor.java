package eu.eurofel.components;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.eurofel.base.Mode;
import eu.eurofel.services.DaoService;
import eu.eurofel.services.FacilityService;

@Events({ FacilityEditor.CANCEL_CREATE, FacilityEditor.SUCCESSFUL_CREATE,
		FacilityEditor.FAILED_CREATE, FacilityEditor.TO_UPDATE,
		FacilityEditor.CANCEL_UPDATE, FacilityEditor.SUCCESSFUL_UPDATE,
		FacilityEditor.FAILED_UPDATE, FacilityEditor.SUCCESFUL_DELETE,
		FacilityEditor.FAILED_DELETE })
public class FacilityEditor {
	public static final String CANCEL_CREATE = "cancelCreate";
	public static final String SUCCESSFUL_CREATE = "successfulCreate";
	public static final String FAILED_CREATE = "failedCreate";
	public static final String TO_UPDATE = "toUpdate";
	public static final String CANCEL_UPDATE = "cancelUpdate";
	public static final String SUCCESSFUL_UPDATE = "successfulUpdate";
	public static final String FAILED_UPDATE = "failedUpdate";
	public static final String SUCCESFUL_DELETE = "successfulDelete";
	public static final String FAILED_DELETE = "failedDelete";

	// Parameters

	@Parameter
	@Property
	private Mode _mode;

	@Parameter
	@Property
	private Long _facilityId;

	@SuppressWarnings("unused")
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String _zone;

	// Screen fields

	@Property
	private eu.eurofel.entities.Facility _facility;

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
	private DaoService _daoService;

	@SuppressWarnings("unused")
	@Inject
	private FacilityService facilityService;

	@InjectComponent
	private Zone _time2Zone;

	@Inject
	@Path("context:layout/images/delete_facility.png")
	private Asset _deleteFacilityIcon;

	@Inject
	@Path("context:layout/images/update_facility.png")
	private Asset _updateFacilityIcon;

	@Inject
	@Path("context:layout/images/cancel_icon.png")
	private Asset _cancelIcon;

	@Inject
	@Path("context:layout/images/save_icon.png")
	private Asset _saveIcon;

	// The code

	// setupRender() is called by Tapestry right before it starts rendering the
	// component.

	void setupRender() {

		if (_facilityId == null) {
			_facility = null;
		} else {
			if (_facility == null) {
				_facility = _daoService.getFacilityDao().selectFacilityById(
						_facilityId.toString());
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
		_facility = new eu.eurofel.entities.Facility();
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
			Long fid = _daoService.getFacilityDao().insertFacility(_facility);
			_facility = _daoService.getFacilityDao().selectFacilityById(
					fid.toString());
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
				new Object[] { _facility.getId() }, getMyCallback());
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

	boolean onToUpdate(Long personId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Handle event "cancelUpdate"

	boolean onCancelUpdate(Long personId) {
		// Return false, which means we haven't handled the event so bubble it
		// up.
		// This method is here solely as documentation, because without this
		// method the event would bubble up anyway.
		return false;
	}

	// Component "updateForm" triggers the PREPARE event when it is rendered or
	// submitted

	void onPrepareFromUpdateForm(Long facilityId) {
		_facilityId = facilityId;
		if (_facility == null) {
			// Get objects for the form fields to overlay.
			_facility = _daoService.getFacilityDao().selectFacilityById(
					_facilityId.toString());
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
			_daoService.getFacilityDao().updateFacility(_facility);
		} catch (Exception e) {
			// Display the cause. In a real system we would try harder to get a
			// user-friendly message.
			_updateForm.recordError(e.getLocalizedMessage());
		}
	}

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
				new Object[] { _facilityId }, getMyCallback());
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
				new Object[] { _facilityId }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	// /////////////////////////////////////////////////////////////////////
	// PING
	// /////////////////////////////////////////////////////////////////////

	// Handle event "ping"
	// Isn't called if the link is clicked before the DOM is fully loaded. See
	// https://issues.apache.org/jira/browse/TAP5-1 .
	Object onActionFromRefreshZone() {
		// Here we can do whatever updates we want, then return the content we
		// want rendered.
		//System.out.print("Pinging...:");
//		if (_facility != null) {
//			System.out.println(_facility.getAttributeUpdaterURL());
//			facilityService.pingFacility(_facility.getId());
//		}
		//System.out.println();
		return _time2Zone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// DELETE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "delete"

	Object onDelete(Long facilityId) {
		_facilityId = facilityId;
		_facility = _daoService.getFacilityDao().selectFacilityById(
				facilityId.toString());
		// Handle null person in the template.

		boolean successfulDelete = false;

		if (_facility != null) {

			// Delete the person from the database unless they've been
			// modified elsewhere

			try {
				_daoService.getFacilityDao().deleteFacility(
						new Long(_facility.getId()).toString());
				successfulDelete = true;

			} catch (Exception e) {
				// Display the cause. In a real system we would try harder
				// to get a user-friendly message.
				_deleteMessage = e.getLocalizedMessage();
			}

		}

		if (successfulDelete) {
			// Trigger new event "successfulDelete" (which in this example will
			// bubble up to the page).
			// When the event has been handled, Tapestry will call my callback
			// with the result.
			_componentResources.triggerEvent(SUCCESFUL_DELETE,
					new Object[] { _facilityId }, getMyCallback());
		} else {
			// Trigger new event "failedDelete" (which in this example will
			// bubble up to the page).
			// When the event has been handled, Tapestry will call my callback
			// with the result.
			_componentResources.triggerEvent(FAILED_DELETE,
					new Object[] { _facilityId }, getMyCallback());
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

	public Asset getDeleteFacilityIcon() {
		return _deleteFacilityIcon;
	}

	public Asset getUpdateFacilityIcon() {
		return _updateFacilityIcon;
	}

	public Asset getCancelIcon() {
		return _cancelIcon;
	}

	public Asset getSaveIcon() {
		return _saveIcon;
	}

	public void setFacilityService(FacilityService facilityService) {
		this.facilityService = facilityService;
	}

	public Date getPingTime() {
		//System.out.println("get ping time");
		return new Date();
	}
}
