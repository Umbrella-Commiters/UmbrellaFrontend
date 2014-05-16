package eu.eurofel.services.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import eu.eurofel.Messages;
import eu.eurofel.entities.BridgeFederation;
import eu.eurofel.entities.EAAAccount;
import eu.eurofel.entities.Notification;
import eu.eurofel.services.DisseminationService;
import eu.eurofel.services.EAAService;
import eu.eurofel.services.NotificationService;
import eu.eurofel.util.Constants;
import eu.eurofel.util.EAAHash;
import eu.eurofel.util.RetrieveNavigation;

public class EAAServiceImpl
    implements EAAService
{

    private ResourceBundle eurofel = ResourceBundle.getBundle( "eaa" );

    DirContext ctx;

    private DisseminationService disseminationService;

    private NotificationService notificationService;

    public EAAServiceImpl()
    {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put( Context.INITIAL_CONTEXT_FACTORY, eurofel.getString( "eaa.initial_context_factory" ) );
        env.put( Context.PROVIDER_URL, eurofel.getString( "eaa.provider_url" ) );
        env.put( Context.SECURITY_AUTHENTICATION, eurofel.getString( "eaa.security_authentication" ) );
        env.put( Context.SECURITY_PRINCIPAL, eurofel.getString( "eaa.security_principal" ) ); // specify
                                                                                              // the
                                                                                              // username
        env.put( Context.SECURITY_CREDENTIALS, eurofel.getString( "eaa.security_credentials" ) );

        try
        {
            ctx = new InitialDirContext( env );
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see eu.eurofel.services.EAAService#createAccount(eu.eurofel.entities.EAAAccount )
     */

    public void createAccount( EAAAccount eAAAccount )
        throws Exception
    {

        // bind the account to the newpeople ou
        ctx.bind( "uid=" + eAAAccount.getUid() + "," + Constants.NEW_PEOPLE_DN, eAAAccount );

        // create a notification to inform the user
        //
        // The activation URL is:
        // https://umbrella.psi.ch/euu/validate?uid=<uid>&uuid=<uuid>
        //
        Notification notification = new Notification();
        notification.setSubject( "Please activate your Umbrella account" );
        String body =
            "Dear " + eAAAccount.getUid() + ",\n\nThe activation URL is: " + Messages.getString( "eaa.url" )
                + "euu/validate?uid=" + eAAAccount.getUid() + "&uuid=" + eAAAccount.getEaahash();
        if ( eAAAccount.getTarget() != null && !eAAAccount.getTarget().equals( "" ) )
        {
            body = body + "&target=" + eAAAccount.getTarget();
        }
        notification.setBody( body );
        notificationService.notify( notification, eAAAccount );
    }

    /*
     * (non-Javadoc)
     * @see eu.eurofel.services.EAAService#activateAccount(eu.eurofel.entities.EAAAccount )
     */

    @Override
    public boolean changeEmail( String uid, String uuid, String email )
        throws Exception
    {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime( date );

        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );
        int hour = calendar.get( Calendar.HOUR_OF_DAY );

        EAAAccount eAAAccount = new EAAAccount( findAccountByHash( uuid ) );
        EAAAccount ea1 = new EAAAccount();
        ea1.setEmail( email );

        // create a notification to verify the email
        //
        // The activation URL is:
        // https://umbrella.psi.ch/euu/validate?uid=<uid>&uuid=<uuid>
        //
        Notification notification = new Notification();
        notification.setSubject( "Request to change your Email at Umbrella" );
        String body =
            "Dear " + uid + ",\n\nIf you want to change the email address for your account to: \"" + email
                + "\" please click on following link: " + Messages.getString( "eaa.url" ) + "euu/changemail?uuid="
                + uuid + "&email=" + email + "&checksum="
                + EAAHash.getSHA1Hash( email + eAAAccount.getEaakey() + year + month + day + hour );

        notification.setBody( body );
        notificationService.notify( notification, ea1 );
        System.out.println( body );
        return false;
    }

    @Override
    public void createBridge( BridgeFederation federationBridge )
        throws Exception
    {

        // bind the account to the bridge ou with a distinct uid == UUID
        System.out.println( "CONSTANTS: " + Constants.BRIDGE_DN );
        ctx.bind( "uid=" + UUID.randomUUID().toString() + "," + Constants.BRIDGE_DN, federationBridge );

    }

    @Override
    public boolean removeBridge( String uid )
    {
        String dn = uid + "," + Constants.BRIDGE_DN;
        try
        {
            ctx.unbind( dn );
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void activateAccount( EAAAccount eAAAccount )
        throws NamingException
    {

        ctx.rename( "uid=" + eAAAccount.getUid() + "," + Constants.NEW_PEOPLE_DN, "uid=" + eAAAccount.getUid() + ","
            + Constants.PEOPLE_DN );
    }

    /*
     * (non-Javadoc)
     * @see eu.eurofel.services.EAAService#updateAccount(eu.eurofel.entities.EAAAccount )
     */

    public void updateAccount( EAAAccount eAAAccount )
        throws NamingException
    {

    }

    /*
     * (non-Javadoc)
     * @see eu.eurofel.services.EAAService#findAccount(java.lang.String)
     */

    public Attributes findAccount( String uid )
        throws NamingException
    {
        String query = "uid=" + uid;
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        Attributes attrs;
        try
        {
            attrs = ctx.search( "", query, ctrl ).next().getAttributes();
        }
        catch ( Exception e )
        {
            throw new NamingException( e.getLocalizedMessage() );
        }
        return attrs;

    }

    /*
     * (non-Javadoc)
     * @see eu.eurofel.services.EAAService#isAccountAvailable(java.lang.String)
     */

    public boolean isAccountAvailable( String uid )
        throws NamingException
    {

        String query = "uid=" + uid;
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        NamingEnumeration<SearchResult> enumeration = ctx.search( "", query, ctrl );
        if ( enumeration.hasMore() )
        {
            throw new NamingException( "Account exists" );
        }
        else
        {
            return false;
        }

    }

    /*
     * (non-Javadoc)
     * @see eu.eurofel.services.EAAService#isEmailAvailable(java.lang.String)
     */

    public boolean isEmailAvailable( String email )
        throws NamingException
    {
        email = EAAHash.getSHA1Hash( email.toLowerCase() );
        String query = "mail=" + email;
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        NamingEnumeration<SearchResult> enumeration = ctx.search( "", query, ctrl );
        while ( enumeration.hasMore() )
        {
            // loop through the results and find the email
            EAAAccount acc = new EAAAccount( enumeration.next().getAttributes() );
            if ( acc.getEmail().equals( email ) )
            {
                throw new NamingException( "Email exists" );
            }
        }
        return false;

    }

    public DisseminationService getDisseminationService()
    {
        return disseminationService;
    }

    public void setDisseminationService( DisseminationService disseminationService )
    {
        this.disseminationService = disseminationService;
    }

    public void setNotificationService( NotificationService notificationService )
    {
        this.notificationService = notificationService;
    }

    public Attributes findAccountByHash( String eaahash )
        throws NamingException
    {

        String query = "(EAAHash=" + eaahash + ")";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        Attributes attrs;
        try
        {
            attrs = ctx.search( "", query, ctrl ).next().getAttributes();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            throw new NamingException( e.getLocalizedMessage() );
        }
        return attrs;
    }

    public boolean hasAccountEmail( String hash, String email )
        throws NamingException
    {
        String query = "(&(mail=" + email + ") (EAAHash=" + hash + "))";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        NamingEnumeration<SearchResult> enumeration = ctx.search( "", query, ctrl );
        if ( enumeration.hasMore() )
        {
            return true;
        }
        else
        {
            throw new NamingException( "Wrong email for this account" );
        }

    }

    public boolean validatePassword( String uid, String pwd )
    {

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put( Context.INITIAL_CONTEXT_FACTORY, eurofel.getString( "eaa.initial_context_factory" ) );
        env.put( Context.PROVIDER_URL, eurofel.getString( "eaa.provider_url" ) );
        env.put( Context.SECURITY_AUTHENTICATION, eurofel.getString( "eaa.security_authentication" ) );
        env.put( Context.SECURITY_PRINCIPAL, "uid=" + uid + "," + eurofel.getString( "eaa.people_root" ) ); // specify
        // the
        // username
        env.put( Context.SECURITY_CREDENTIALS, pwd );

        try
        {
            // Create initial context
            DirContext ctx = new InitialDirContext( env );

            // Close the context when we're done
            ctx.close();
            return true;
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public void sendUsernameToEmail( String email )
    {

    }

    public boolean changePassword( String uid, String oldpwd, String newpwd )
    {

        if ( validatePassword( uid, oldpwd ) )
        {
            ModificationItem[] mods = new ModificationItem[1];

            Attribute mod0 = new BasicAttribute( "userPassword", newpwd );

            mods[0] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE, mod0 );

            try
            {
                ctx.modifyAttributes( "uid=" + uid + "," + eurofel.getString( "eaa.people_root" ), mods );
                return true;
            }
            catch ( NamingException e )
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public Attributes findAccountByEmail( String email )
        throws NamingException
    {
        String query = "(mail=" + EAAHash.getSHA1Hash( email.toLowerCase() ) + ")";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        Attributes attrs;
        try
        {
            attrs = ctx.search( "", query, ctrl ).next().getAttributes();
        }
        catch ( Exception e )
        {
            throw new NamingException( e.getLocalizedMessage() );
        }
        return attrs;
    }

    public Attributes findAccountByEmailAndUid( String email, String uid )
        throws NamingException
    {
        String query = "(&(mail=" + EAAHash.getSHA1Hash( email.toLowerCase() ) + ")(uid=" + uid + "))";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        Attributes attrs;
        try
        {
            attrs = ctx.search( "", query, ctrl ).next().getAttributes();
        }
        catch ( Exception e )
        {
            throw new NamingException( e.getLocalizedMessage() );
        }
        return attrs;
    }

    public boolean changeEmail( String uid, String newemail )
    {

        ModificationItem[] mods = new ModificationItem[1];
        Attribute mod0 = new BasicAttribute( "mail", newemail );
        mods[0] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE, mod0 );
        try
        {
            ctx.modifyAttributes( "uid=" + uid + "," + eurofel.getString( "eaa.people_root" ), mods );
            return true;
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword( String uid, String newpwd )
    {

        ModificationItem[] mods = new ModificationItem[1];
        Attribute mod0 = new BasicAttribute( "userPassword", newpwd );
        mods[0] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE, mod0 );
        try
        {
            ctx.modifyAttributes( "uid=" + uid + "," + eurofel.getString( "eaa.people_root" ), mods );
            return true;
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addResetPwUUID( String uid, String uuid )
    {

        ModificationItem[] mods = new ModificationItem[2];
        Attribute mod0 = new BasicAttribute( "EAAResetPwUUID", uuid );
        Attribute mod1 = new BasicAttribute( "EAAResetPwDate", new Long( new Date().getTime() ).toString() );
        mods[0] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE, mod0 );
        mods[1] = new ModificationItem( DirContext.REPLACE_ATTRIBUTE, mod1 );
        try
        {
            ctx.modifyAttributes( "uid=" + uid + "," + eurofel.getString( "eaa.people_root" ), mods );
            return true;
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeResetPwUUID( String uid )
    {

        ModificationItem[] mods = new ModificationItem[2];
        Attribute mod0 = new BasicAttribute( "EAAResetPwUUID", "" );
        Attribute mod1 = new BasicAttribute( "EAAResetPwDate", "" );
        mods[0] = new ModificationItem( DirContext.REMOVE_ATTRIBUTE, mod0 );
        mods[1] = new ModificationItem( DirContext.REMOVE_ATTRIBUTE, mod1 );
        try
        {
            ctx.modifyAttributes( "uid=" + uid + "," + eurofel.getString( "eaa.people_root" ), mods );
            return true;
        }
        catch ( NamingException e )
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public NamingEnumeration<?> findBridges( String eaahash )
    {
        // (BridgeFederationUmbrellaUID="5919673a-03c8-46bc-8870-1044e47f9072")
        String query = "(BridgeFederationUmbrellaUID=" + eaahash + ")";
        System.out.println( query );
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );

        NamingEnumeration<?> attrs = null;
        try
        {
            attrs = ctx.search( Constants.BRIDGE_DN, query, ctrl );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return attrs;
    }

    @Override
    public NamingEnumeration<?> findBridgesByBridgeUid( String uid )
    {
        // (BridgeFederationUmbrellaUID="5919673a-03c8-46bc-8870-1044e47f9072")
        String query = "(BridgeFederationUID=" + uid + ")";
        System.out.println( query );
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );

        NamingEnumeration<?> attrs = null;
        try
        {
            attrs = ctx.search( Constants.BRIDGE_DN, query, ctrl );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        return attrs;
    }

    public Attributes findAccountByResetUUID( String uuid, String uid, String email )
        throws NamingException
    {
        String query =
            "(&(mail=" + EAAHash.getSHA1Hash( email.toLowerCase() ) + ")(uid=" + uid + ")(EAAResetPwUUID=" + uuid
                + "))";
        System.out.println( query );
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        Attributes attrs;
        try
        {
            attrs = ctx.search( "", query, ctrl ).next().getAttributes();
        }
        catch ( Exception e )
        {
            throw new NamingException( e.getLocalizedMessage() );
        }
        return attrs;
    }

    public boolean checkForValidResetUUID( String uuid )
        throws NamingException
    {
        String query = "(&(EAAResetPwUUID=" + uuid + "))";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope( SearchControls.SUBTREE_SCOPE );
        NamingEnumeration<SearchResult> enumeration = ctx.search( "", query, ctrl );

        // check for date
        while ( enumeration.hasMore() )
        {
            SearchResult result = enumeration.next();
            if ( result.getAttributes() != null )
            {
                Attribute attr = result.getAttributes().get( "EAAResetPWDate" );
                if ( attr != null )
                {

                    long stamp = new Long( attr.get().toString() ).longValue();
                    ;

                    long date = new Date().getTime();
                    if ( date - stamp < 1800000 && date - stamp > 0 )
                    {
                        return true;
                    }
                }

            }
        }
        return false;

    }

    @Override
    public void fetchLayout()
    {
        try
        {
            RetrieveNavigation.run();
        }
        catch ( IllegalStateException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

}
