package eu.eurofel.pages.bridge;

import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.NamingException;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import eu.eurofel.entities.BridgeFederation;
import eu.eurofel.entities.FederationBridge;
import eu.eurofel.entities.UserSession;
import eu.eurofel.services.EAAService;

@Secure
public class BridgeSelector
{

    @SessionState( create = true )
    private UserSession userSession;

    @Property
    private FederationBridge retriever;

    @Inject
    private Request _request;

    @Inject
    private EAAService service;

    // @Property
    // private String loginname;

    // @Property
    // private String password;

    @InjectPage
    private MyBridges mybridges;

    // Generally useful bits and pieces

    @Component( id = "login" )
    private Form form;

    //
    // @Component(id = "loginname")
    // private TextField loginnameField;
    //
    // @Component(id = "password")
    // private TextField passwordField;

    void onActivate()
        throws NamingException
    {
        if ( retriever == null )
        {
            retriever = new FederationBridge();
        }
        if ( userSession.getFederation() != null )
        {
            retriever = userSession.getFederation();
        }
    }

    public boolean getExistingMatch()
    {
        boolean found = false;
        System.out.println( "LOGGED IN EDUGAIN: " + getLoggedinEduGain() );

        try
        {
            if ( getLoggedinEduGain()
                && null != service.findBridgesByBridgeUid( userSession.getFederation().getFederationUID() ).next() )
            {
                found = true;
            }
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return found;

    }

    void onValidateFromConnect()
        throws Exception
    {

        // Now at this point we both need values inside the user session from
        // the remote federation and from the Umbrella as well
        if ( userSession != null && userSession.getFederation() != null
            && userSession.getFederation().getFederationUID() != null && _request.getHeader( "EAAHash" ) != null )
        {

            BridgeFederation bridge = new BridgeFederation();
            bridge.setBridgeFederationSrc( userSession.getFederation().getFederationName() );
            bridge.setBridgeFederationUid( userSession.getFederation().getFederationUID() );
            bridge.setBridgeUmbrellaUid( _request.getHeader( "EAAHash" ) );
            bridge.setBridgeFederationUmbrellaUsername( _request.getHeader( "uid" ) );
            service.createBridge( bridge );

        }
    }

    public String getUsername()
    {

        if ( userSession != null && userSession.isLoggedIn() && !userSession.getUserName().equals( "" ) )
        {
            return userSession.getUserName();
        }
        return "";
    }

    public boolean getLoggedin()
    {
        boolean found = false;

        if ( userSession != null && userSession.isLoggedIn() && _request != null
            && _request.getSession( false ) != null
            && ( _request.getHeader( "EAAHash" ) == null || _request.getHeader( "EAAHash" ).equals( "" ) ) )
        {
            _request.getSession( false ).invalidate();
            userSession = null;
        }
        if ( userSession != null && userSession.isLoggedIn() )
        {
            found = true;
        }

        return found;
    }

    public boolean getLoggedinEduGain()
    {
        boolean found = false;
        System.out.println( "userSession: " + userSession );
        if ( userSession.getFederation() != null )
        {
            System.out.println( userSession.getFederation().getFederationUID() );
        }
        else
        {
            System.out.println( "no federation in userSesssion" );
        }
        if ( userSession != null && userSession.getFederation() != null
            && userSession.getFederation().getFederationUID() != null
            && !userSession.getFederation().getFederationUID().equals( "" ) )
        {
            found = true;
        }
        return found;
    }

    Object onValidateFromEduGain()
        throws MalformedURLException
    {
        URL url =
            new URL(
                     "https://umbrella.psi.ch/Shibboleth.sso/Login?target=https://umbrella.psi.ch/euu/bridge/endpoint/EduGain" );
        return url;
    }

    Object onValidateFromLogin()
        throws NamingException, MalformedURLException
    {
        // if (loginname == null || loginname.trim().equals("")) {
        // form.recordError(loginnameField, "Username is required");
        // }
        // if (password == null || password.trim().equals("")) {
        // form.recordError(passwordField, "Password is required");
        // }

        // if (!service.validatePassword(loginname, password)) {
        // if(!userSession.getIdP().equals("https://umbrella.psi.ch/idp/shibboleth")){
        URL url =
            new URL( "https://umbrella.psi.ch/Shibboleth.sso/Login?target=https://umbrella.psi.ch/euu/bridge/selector" );
        return url;
        // form.recordError("Wrong credentials.");
        // } else {
        // // we need to create the bridge
        //
        // try {
        // BridgeFederation bridge = new BridgeFederation();
        // bridge.setBridgeFederationSrc(userSession.getFederation()
        // .getFederationName());
        // bridge.setBridgeFederationUid(userSession.getFederation()
        // .getFederationUID());
        //
        // // we need to retrieve the EAAHash
        // Attributes attrs = service.findAccount(loginname);
        // Attribute eaahash = attrs.get("EAAHash");
        //
        //
        //
        //
        // bridge.setBridgeUmbrellaUid(eaahash.get().toString());
        // service.createBridge(bridge);
        // } catch (Exception e) {
        // e.printStackTrace();
        // form.recordError("Something went wrong with connecting your Umbrella Account");
        // }
        //
        // }
    }

    Object onSuccess()
    {
        return mybridges;
    }
}
