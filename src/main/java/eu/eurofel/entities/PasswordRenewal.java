package eu.eurofel.entities;

import java.io.Serializable;

import org.apache.tapestry5.beaneditor.Validate;

public class PasswordRenewal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4883766562996949359L;

	@Validate("required")
	private String oldPassword;

	@Validate("required,regexp")
	private String newPassword1;

	@Validate("required,regexp")
	private String newPassword2;

	private String eaahash;

	private String eaakey;

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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword1() {
		return newPassword1;
	}

	public void setNewPassword1(String newPassword1) {
		this.newPassword1 = newPassword1;
	}

	public String getNewPassword2() {
		return newPassword2;
	}

	public void setNewPassword2(String newPassword2) {
		this.newPassword2 = newPassword2;
	}

}
