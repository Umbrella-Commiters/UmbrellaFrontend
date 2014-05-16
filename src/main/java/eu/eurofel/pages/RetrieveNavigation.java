package eu.eurofel.pages;

import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import eu.eurofel.entities.UserSession;
import eu.eurofel.services.EAAService;

@Secure
public class RetrieveNavigation
{

    @SuppressWarnings("unused")
    @SessionState(create = false)
    private UserSession userSession;

    @Inject
    private EAAService service;

    void onActionFromRetrieve() {
        service.fetchLayout();
    }
}
