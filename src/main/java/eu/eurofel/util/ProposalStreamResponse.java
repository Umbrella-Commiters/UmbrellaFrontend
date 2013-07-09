package eu.eurofel.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import eu.eurofel.entities.Proposal;
import eu.eurofel.services.ProposalService;

public class ProposalStreamResponse implements StreamResponse {

	private InputStream _is;
	private String _filename = "proposal.xml";


	public ProposalStreamResponse(Proposal proposal, ProposalService proposalService) {
		try {
			this._is = proposalService.getInputStreamFromProposal(proposal);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public String getContentType() {
		return "application/xml; charset=utf-8";
	}

	
	public InputStream getStream() throws IOException {
		return _is;
	}

	
	public void prepareResponse(Response response) {
		response.setHeader("Content-Disposition", "attachment; filename="
				+ _filename);
	}

}
