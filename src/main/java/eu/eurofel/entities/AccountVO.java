package eu.eurofel.entities;

import java.io.Serializable;
import java.util.Date;

public class AccountVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8225435845538025972L;

	private String uid;

	private Date birthdate;

	private String email;

	private String eaahash;

	private String eaakey;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEaahash() {
		return eaahash;
	}

	public void setEaahash(String eaahash) {
		this.eaahash = eaahash;
	}

	public String getEaakey() {
		return eaakey;
	}

	public void setEaakey(String eaakey) {
		this.eaakey = eaakey;
	}
}
