package com.appspot.mademea.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.appspot.mademea.client.ProposalService;
import com.appspot.mademea.client.domain.Proposal;
import com.appspot.mademea.client.domain.exception.TooMuchProposalsException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProposalServiceImpl extends RemoteServiceServlet implements
										 ProposalService {
	private static final int PROPOSAL_QUOTA = 5000;	
	private List<Proposal> proposals = new ArrayList<Proposal>();
	  
	public void addProposal(Proposal newProposal)
			throws IllegalArgumentException, TooMuchProposalsException {
		if (proposals.size() < PROPOSAL_QUOTA) {
			proposals.add(newProposal);
		} else {
			throw new TooMuchProposalsException("Proposals is limited to " + PROPOSAL_QUOTA);
		}
		
	}

	public Collection<Proposal> getProposals() {
		return proposals;
	} 

}
