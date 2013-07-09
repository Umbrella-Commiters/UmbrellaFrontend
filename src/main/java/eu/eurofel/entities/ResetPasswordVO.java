package eu.eurofel.entities;

import java.io.Serializable;
import java.util.Date;

public class ResetPasswordVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1512095999823459038L;

	private String verificationCode = "";
	
	private Date birthdate;

	private String email;

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

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

}
