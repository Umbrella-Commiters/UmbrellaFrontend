package eu.eurofel.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class LayoutDelegate {

	@SuppressWarnings("unused")
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
	private String title;

	@SuppressWarnings("unused")
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private String sidebarTitle;

	@SuppressWarnings("unused")
	@Property
	@Parameter(defaultPrefix = BindingConstants.LITERAL)
	private Block sidebar;

	@InjectComponent
	private LayoutPSI _layoutpsi;

	@InjectComponent
	private Layout _layoutnorm;

	@Inject
	private Request _request;

	public Object getLayout() {
		String serverName = _request.getServerName();
		if (serverName.endsWith("desy.de")) {
			return _layoutpsi;
		} else if (serverName.endsWith("psi.ch")) {
			return _layoutpsi;
		} else {
			return _layoutnorm;
		}
	}

}
