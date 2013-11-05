package eu.eurofel.entities;

public interface UserSession {

	public abstract boolean isLoggedIn();

	public abstract void setLoggedIn(boolean loggedIn);
	
	public abstract String getUserName();
	
	public abstract void setUserName(String userName);

	public abstract String getResetUUID();
	
	public abstract void setResetUUID(String uuid);
	
	public abstract String getTarget();
	
	public abstract void setTarget(String target);
	
	public abstract FederationBridge getFederation();
	
	public abstract void setFederation(FederationBridge federationBridge);
	
}
