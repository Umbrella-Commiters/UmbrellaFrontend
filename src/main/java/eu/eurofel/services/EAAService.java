package eu.eurofel.services;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.BridgeFederation;
import eu.eurofel.entities.EAAAccount;

@Service
public interface EAAService
{

    public abstract void createAccount( EAAAccount eAAAccount )
        throws Exception;

    public abstract void activateAccount( EAAAccount eAAAccount )
        throws NamingException;

    public abstract void updateAccount( EAAAccount eAAAccount )
        throws NamingException;

    public abstract Attributes findAccount( String uid )
        throws NamingException;

    public abstract Attributes findAccountByHash( String eaahash )
        throws NamingException;

    public abstract boolean isAccountAvailable( String uid )
        throws NamingException;

    public abstract boolean isEmailAvailable( String email )
        throws NamingException;

    public boolean hasAccountEmail( String hash, String email )
        throws NamingException;

    public abstract void createBridge( BridgeFederation federationBridge )
        throws Exception;

    public boolean removeBridge( String uid );

    public NamingEnumeration<?> findBridges( String eaahash );

    public NamingEnumeration<?> findBridgesByBridgeUid( String uid );

    /*
     * public abstract boolean hasAccountEmail(String hash, String email) throws NamingException;
     */
    public abstract boolean validatePassword( String uid, String pwd );

    public boolean changePassword( String uid, String oldpwd, String newpwd );

    public boolean changePassword( String uid, String newpwd );

    public boolean changeEmail( String uid, String uuid, String email )
        throws Exception;

    public boolean changeEmail( String uid, String newemail );

    public boolean addResetPwUUID( String uid, String uuid );

    public boolean removeResetPwUUID( String uid );

    public boolean checkForValidResetUUID( String uuid )
        throws NamingException;

    public Attributes findAccountByEmail( String email )
        throws NamingException;

    public Attributes findAccountByResetUUID( String uuid, String uid, String email )
        throws NamingException;

    public Attributes findAccountByEmailAndUid( String email, String uid )
        throws NamingException;
    
    public void fetchLayout();

}