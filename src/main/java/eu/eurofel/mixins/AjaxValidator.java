package eu.eurofel.mixins;

import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import(library = "ajax_validator.js")
public class AjaxValidator {

	// Useful bits and pieces

	@Inject
	private ComponentResources resources;

	@Environmental
	private JavaScriptSupport renderSupport;

	/**
	 * The element we attach ourselves to
	 */
	@InjectContainer
	private ClientElement element;

	// The code

	void afterRender() {
		String listenerURI = resources.createEventLink("ajaxValidate")
				.toAbsoluteURI();
		String elementId = element.getClientId();

		JSONObject spec = new JSONObject();
		spec.put("listenerURI", listenerURI);
		spec.put("elementId", elementId);

		renderSupport.addScript("new AjaxValidator(%s)", spec.toString());
	}
}
