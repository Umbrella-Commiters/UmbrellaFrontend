package eu.eurofel.entities;

import java.io.Serializable;
import java.util.Date;

public class Link implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7258283626844497020L;

	private String description;

	private String title;

	private String author;

	private String link;

	private Date date;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\t Title: " + this.title + "\n");
		sb.append("\t Author: " + this.author + "\n");
		sb.append("\t Link: " + this.link + "\n");
		sb.append("\t Date: " + this.date + "\n");
		return sb.toString();
	}

}
