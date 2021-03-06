package eu.eurofel.entities;

import java.io.Serializable;

public class UserSessionImpl implements UserSession, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3872188269849099771L;
	private boolean loggedIn;
	private String userName;
	private String uuid;
	private String target;
	private FederationBridge federation;
	private String idp;

	public UserSessionImpl() {
		this.loggedIn = false;
		this.userName = "";
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResetUUID() {
		return uuid;
	}

	public void setResetUUID(String uuid) {
		this.uuid = uuid;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public FederationBridge getFederation() {
		// TODO Auto-generated method stub
		return federation;
	}

	public void setFederation(FederationBridge federationBridge) {
		this.federation = federationBridge;
	}

	
	public String getIdP() {
		return idp;
	}

	
	public void setIdP(String idp) {
		this.idp = idp;
	}

}
