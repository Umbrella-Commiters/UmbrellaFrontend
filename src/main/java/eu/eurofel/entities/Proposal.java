package eu.eurofel.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Proposal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6801069343897695311L;

	private String title;

	private Proposer proposer;

	private Proposer investigator;

	private List<Proposer> coproposer = new ArrayList<Proposer>();

	private List<ProposalSection> sections = new ArrayList<ProposalSection>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Proposer getProposer() {
		return proposer;
	}

	public void setProposer(Proposer proposer) {
		this.proposer = proposer;
	}

	public Proposer getInvestigator() {
		return investigator;
	}

	public void setInvestigator(Proposer investigator) {
		this.investigator = investigator;
	}

	public List<Proposer> getCoproposer() {
		return coproposer;
	}

	public void setCoproposer(List<Proposer> coproposer) {
		this.coproposer = coproposer;
	}

	public List<ProposalSection> getSections() {
		return sections;
	}

	public void setSections(List<ProposalSection> sections) {
		this.sections = sections;
	}

	public ProposalSection getSectionById(String id) {
		for (ProposalSection ps : sections) {
			if (ps.getId().equals(id)) {
				return ps;
			}
		}
		return null;
	}

	public boolean containsSection(Section section) {
		for (ProposalSection proposalSection : this.getSections()) {
			if (proposalSection.getSection() != null && proposalSection.getSection().equals(section)) {
				return true;
			}
		}
		return false;
	}
	
	public void removeSection(String id){
		for(Iterator<ProposalSection> it = sections.iterator(); it.hasNext(); ){
			ProposalSection ps = it.next();
			if(ps.getId().equals(id)){
				it.remove();
				break;
			}
		}
	}
}
