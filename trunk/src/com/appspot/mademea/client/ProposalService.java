package com.appspot.mademea.client;

import java.util.Collection;

import com.appspot.mademea.client.domain.Proposal;
import com.appspot.mademea.client.domain.exception.TooMuchProposalsException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("proposal")
public interface ProposalService extends RemoteService {
	void addProposal(Proposal newProposal) throws IllegalArgumentException, TooMuchProposalsException;
	Collection<Proposal> getProposals();
}
