package eu.eurofel.entities;

import java.io.Serializable;

public class Proposer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1433257465357565211L;

	private String name;
	private String institute;
	private String department;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
