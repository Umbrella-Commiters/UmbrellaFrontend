package eu.eurofel.pages.account;

import javax.naming.NamingException;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import eu.eurofel.annotation.Private;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.DaoService;
import eu.eurofel.services.EAAService;

@Private
@Secure
public class UserInformation {

	@SuppressWarnings("unused")
	@Property
	@Persist
	private String eaahash;

	@Inject
	private EAAService service;
//
//	@Inject
//	private DaoService _daoService;

	@Inject
	private Request _request;

	@SuppressWarnings("unused")
	@SessionState(create = false)
	private UserSession userSession;
	
    @InjectComponent("update")
    private Form form;
    
//
//	@Property
//	private eu.eurofel.entities.UserInformation userInformation = new eu.eurofel.entities.UserInformation();

	@Property
	private EAAAccount account = new EAAAccount();
	
	void onActivate() throws NamingException {
		account = new EAAAccount(service.findAccount(_request.getHeader("uid")));
//		System.out.println("Finding Account: " + account);
//		if(account != null){
////			System.out.println("Given Name: " + account.getGivenName());
////			System.out.println("Surname: " + account.getSn());
//		}
	}

	void onSuccess() throws Exception {
		account.setEaahash(_request.getHeader("EAAHash"));
		service.updateAccount(account);
	}
}
