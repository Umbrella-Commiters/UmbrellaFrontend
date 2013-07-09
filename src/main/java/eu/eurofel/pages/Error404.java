package eu.eurofel.pages;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.Request;

public class Error404
{
    @SuppressWarnings("unused")
	@Property
    @Inject
    private Request request;

    @SuppressWarnings("unused")
	@Property
    @Inject
    @Symbol(SymbolConstants.PRODUCTION_MODE)
	private boolean productionMode;
}
