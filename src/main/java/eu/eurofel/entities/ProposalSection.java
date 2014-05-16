package eu.eurofel.entities;

import java.io.Serializable;
import java.util.UUID;

public class ProposalSection implements Serializable,
		Comparable<ProposalSection> {

	/**
	 * 
	 */

	public ProposalSection() {
		this.id = UUID.randomUUID().toString();
	}

	public ProposalSection(Section section) {
		this.section = section;
		this.id = UUID.randomUUID().toString();
	}

	private static final long serialVersionUID = -6739710042163729910L;

	private Section section;

	private String text;

	private String id;

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public int compareTo(ProposalSection o) {
		return o.getSection().name().compareTo(this.getSection().name());
	}

}
