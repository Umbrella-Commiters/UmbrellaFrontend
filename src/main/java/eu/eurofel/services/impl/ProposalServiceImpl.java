package eu.eurofel.services.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;

import eu.eurofel.entities.Proposal;
import eu.eurofel.entities.ProposalSection;
import eu.eurofel.services.ProposalService;

public class ProposalServiceImpl implements ProposalService {

	private XStream xstream;

	public ProposalServiceImpl() {
		xstream = new XStream();
		xstream.alias("proposal", Proposal.class);
		xstream.alias("proposalSection", ProposalSection.class);
	}

	
	public InputStream getInputStreamFromProposal(Proposal proposal)
			throws IOException {
		ByteArrayOutputStream baos = (ByteArrayOutputStream) getOutputStreamFromProposal(proposal);

		return new ByteArrayInputStream(baos.toByteArray());
	}

	
	public Proposal getProposalFromInputStream(InputStream is) {
		return (Proposal) xstream.fromXML(is);
	}

	
	public OutputStream getOutputStreamFromProposal(Proposal proposal)
			throws IOException {
		String xml = xstream.toXML(proposal);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.write(xml, baos);
		return baos;
	}

}
