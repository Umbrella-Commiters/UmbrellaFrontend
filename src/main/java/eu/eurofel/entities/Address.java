package eu.eurofel.entities;

import java.io.Serializable;

import org.apache.tapestry5.beaneditor.Validate;

public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4291832896488172807L;

	private Honorific title;

	private String firstName;

	private String middleInitial;

	private String lastName;

	private Gender gender;

	private String nationality;

	private String affiliation;

	private String email;

	private String phone;
	
	private String eaahash;

	private String eaakey;
	
	@Validate("required")
	public Honorific getTitle() {
		return title;
	}

	public void setTitle(Honorific title) {
		this.title = title;
	}

	@Validate("required")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	@Validate("required")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	@Validate("required")
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Validate("required")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@Validate("required")
	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	@Validate("required,email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Validate("required")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
