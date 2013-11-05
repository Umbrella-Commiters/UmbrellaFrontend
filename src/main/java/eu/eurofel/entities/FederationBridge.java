package eu.eurofel.entities;

import java.io.Serializable;

public class FederationBridge implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1992828501628990693L;
	
	private String federationName;
	
	private String federationUID;
	
	private String federationAuthMethod;

	public String getFederationName() {
		return federationName;
	}

	public void setFederationName(String federationName) {
		this.federationName = federationName;
	}

	public String getFederationUID() {
		return federationUID;
	}

	public void setFederationUID(String federationUID) {
		this.federationUID = federationUID;
	}

	public String getFederationAuthMethod() {
		return federationAuthMethod;
	}

	public void setFederationAuthMethod(String federationAuthMethod) {
		this.federationAuthMethod = federationAuthMethod;
	}
	
	

}
