package eu.eurofel.entities;

import java.io.Serializable;
import java.util.Date;

public class Facility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6691109717673541625L;

	private long id;
	
	private String name;
	
	private String attributeUpdaterURL;

	private String contactPerson;
	
	private String contactPersonEmail;
	
	private Date lastPing;
	
	private boolean enabled;

	
	public String getAttributeUpdaterURL() {
		return attributeUpdaterURL;
	}

	public void setAttributeUpdaterURL(String attributeUpdaterURL) {
		this.attributeUpdaterURL = attributeUpdaterURL;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

	public Date getLastPing() {
		return lastPing;
	}

	public void setLastPing(Date lastPing) {
		this.lastPing = lastPing;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
