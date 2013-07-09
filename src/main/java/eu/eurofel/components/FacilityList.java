package eu.eurofel.components;

import java.util.List;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentEventCallback;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.eurofel.services.DaoService;

@Events( { FacilityList.SELECTED })
public class FacilityList {
	public static final String SELECTED = "selected";


	// Parameters

	@Parameter
	@Property
	private Long _highlightFacilityId;

	@SuppressWarnings("unused")
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	@Property
	private String _zone;

	// Screen fields

	@Property
	@Persist
	private String _partialName;

	@SuppressWarnings("unused")
	@Property
	// Persist the list to support inplace="true" on the Grid
	@Persist
	private List<eu.eurofel.entities.Facility> _facilities;

	@Property
	private eu.eurofel.entities.Facility _facility;

	@SuppressWarnings("unused")
	@Component(id = "list")
	@Property
	private Grid _list;

	// Generally useful bits and pieces

	@Inject
	private DaoService _daoService;

	@Inject
	private ComponentResources _componentResources;

	private Object _zonesToUpdate;

	// The code

	// setupRender() is called by Tapestry right before it starts rendering the component.

	void setupRender() {
		_facilities = _daoService.getFacilityDao().selectFacilitiesByName(_partialName);
		if (_highlightFacilityId == null) {
			// Null is a nuisance because it fails to match the signature of our onSuccess method.
			_highlightFacilityId = -1L;
		}
	}

	Object onSuccessFromAjaxFilterForm(Long highlightPersonId) {
		// Trigger new event "filter" which will bubble up.
		// When the event has been handled, Tapestry will call my callback with the result.
		_componentResources.triggerEvent("filter", new Object[] { highlightPersonId }, getMyCallback());
		// _zonesToUpdate will have been updated by my callback.
		return _zonesToUpdate;
	}

	// Handle event "selected"

	boolean onSelected(Long personId) {
		// Return false, which means we haven't handled the event so bubble it up.
		// This method is here solely as documentation, because without this method the event would bubble up anyway.
		return false;
	}

	// Getters

	public String getLinkCSSClass() {
		if (_facility != null && new Long(_facility.getId()).equals(_highlightFacilityId)) {
			return "active";
		}
		else {
			return "";
		}
	}


	// When you trigger an event you can also specify a callback. When the event has been handled Tapestry will call the
	// callback with the result. This component expects the event handlers will return the zone or zones to update.

	private ComponentEventCallback<Object> getMyCallback() {

		ComponentEventCallback<Object> callback = new ComponentEventCallback<Object>() {
			public boolean handleResult(Object result) {
				_zonesToUpdate = result;
				return false;
			}
		};

		return callback;
	}
}

