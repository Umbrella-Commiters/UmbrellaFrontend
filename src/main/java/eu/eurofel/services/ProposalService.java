package eu.eurofel.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import eu.eurofel.entities.Proposal;

@Service
public interface ProposalService {
	
	public Proposal getProposalFromInputStream(InputStream is);

	public InputStream getInputStreamFromProposal(Proposal proposal) throws IOException;
	
	public OutputStream getOutputStreamFromProposal(Proposal proposal) throws IOException;

}
