package eu.eurofel.pages.facility;

import org.apache.tapestry5.Asset;
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

import eu.eurofel.annotation.Private;
import eu.eurofel.base.Mode;
import eu.eurofel.entities.UserSession;

@Private
@Secure
@Import(stylesheet = "layout/ajaxcomponentscrud.css")
public class ViewFacility {

	// Screen fields

	@Property
	@Persist
	private boolean _highlightZoneUpdates;

	@SuppressWarnings("unused")
	@Property
	private Long _highlightFacilityId;

	@SuppressWarnings("unused")
	@Property
	private Mode _mode;

	@Property
	private Long _facilityId;

	@Inject
	@Path("context:layout/images/create_facility.png")
	private Asset _createFacilityIcon;
	// Generally useful bits and pieces

	@InjectComponent
	private Zone _listZone;

	@InjectComponent
	private Zone _editorZone;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;

	// The code

	// setupRender() is called by Tapestry right before it starts rendering the
	// page.

	void setupRender() {
		_highlightFacilityId = _facilityId;
	}

	// /////////////////////////////////////////////////////////////////////
	// FILTER
	// /////////////////////////////////////////////////////////////////////

	// Handle event "filter" from component "list"
	Object onFilterFromList(Long highlightFacilityId) {
		_highlightFacilityId = highlightFacilityId;
		return _listZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// CREATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toCreate" from this page

	Object onToCreate() {
		_mode = Mode.CREATE;
		_facilityId = null;
		_highlightFacilityId = null;
		return getListAndEditorZones();
	}

	// Handle event "cancelCreate" from component "editor"

	Object onCancelCreateFromEditor() {
		_mode = null;
		_facilityId = null;
		return _editorZone.getBody();
	}

	// Handle event "successfulCreate" from component "editor"

	Object onSuccessfulCreateFromEditor(Long facilityId) {
		_mode = Mode.REVIEW;
		_facilityId = facilityId;
		_highlightFacilityId = facilityId;
		return getListAndEditorZones();
	}

	// Handle event "failedCreate" from component "editor"

	Object onFailedCreateFromEditor() {
		_mode = Mode.CREATE;
		_facilityId = null;
		return _editorZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// REVIEW
	// /////////////////////////////////////////////////////////////////////

	// Handle event "selected" from component "list"

	Object onSelectedFromList(Long facilityId) {
		_mode = Mode.REVIEW;
		_facilityId = facilityId;
		_highlightFacilityId = facilityId;
		return getListAndEditorZones();
	}

	// /////////////////////////////////////////////////////////////////////
	// UPDATE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "toUpdate" from component "editor"

	Object onToUpdateFromEditor(Long facilityId) {
		_mode = Mode.UPDATE;
		_facilityId = facilityId;
		return _editorZone.getBody();
	}

	// Handle event "cancelUpdate" from component "editor"

	Object onCancelUpdateFromEditor(Long facilityId) {
		_mode = Mode.REVIEW;
		_facilityId = facilityId;
		return _editorZone.getBody();
	}

	// Handle event "successfulUpdate" from component "editor"

	Object onSuccessfulUpdateFromEditor(Long facilityId) {
		_mode = Mode.REVIEW;
		_facilityId = facilityId;
		_highlightFacilityId = facilityId;
		return getListAndEditorZones();
	}

	// Handle event "failedUpdate" from component "editor"

	Object onFailedUpdateFromEditor(Long facilityId) {
		return _editorZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// DELETE
	// /////////////////////////////////////////////////////////////////////

	// Handle event "successfulDelete" from component "editor"

	Object onSuccessfulDeleteFromEditor(Long facilityId) {
		_mode = null;
		_facilityId = null;
		_highlightFacilityId = null;
		return getListAndEditorZones();
	}

	// Handle event "failedDelete" from component "editor"

	Object onFailedDeleteFromEditor(Long facilityId) {
		_mode = Mode.REVIEW;
		_facilityId = facilityId;
		return _editorZone.getBody();
	}

	// /////////////////////////////////////////////////////////////////////
	// GETTERS
	// /////////////////////////////////////////////////////////////////////

	private MultiZoneUpdate getListAndEditorZones() {
		return new MultiZoneUpdate("listZone", _listZone.getBody()).add(
				"editorZone", _editorZone.getBody());
	}

	public String getZoneUpdateFunction() {
		return _highlightZoneUpdates ? "highlight" : "show";
	}

	public Asset getCreateFacilityIcon() {
		return _createFacilityIcon;
	}

}
