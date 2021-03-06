package eu.eurofel.entities;

import java.io.Serializable;

public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5321289782496726493L;

	private String subject;
	
	private String body;
	
	private String layout;

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
