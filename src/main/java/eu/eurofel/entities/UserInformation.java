package eu.eurofel.entities;

import java.io.Serializable;

public class UserInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7825623444586679624L;

	private String eaahash;
	
	private String email;
	
	private String givenName;
	
	private String surname;
	
	private String postalAddress;
	
	private String homePostalAddress;
	
	private String telephoneNumber;
	
	private String mobile;
	
	private String homePhone;
	
	private String orcidIdentifier;
	
	private String gender;
	
	private String preferredLanguage;

	public String getEaahash() {
		return eaahash;
	}

	public void setEaahash(String eaahash) {
		this.eaahash = eaahash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getHomePostalAddress() {
		return homePostalAddress;
	}

	public void setHomePostalAddress(String homePostalAddress) {
		this.homePostalAddress = homePostalAddress;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getOrcidIdentifier() {
		return orcidIdentifier;
	}

	public void setOrcidIdentifier(String orcidIdentifier) {
		this.orcidIdentifier = orcidIdentifier;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
