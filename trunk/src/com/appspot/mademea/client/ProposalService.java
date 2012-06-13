package com.appspot.mademea.client;

import java.util.List;

import com.appspot.mademea.shared.ProposalProxy;
import com.appspot.mademea.shared.exception.TooMuchProposalsException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("proposal")
public interface ProposalService extends RemoteService {
	void addProposal(String title, String description) throws IllegalArgumentException, TooMuchProposalsException;
	List<ProposalProxy> getProposals();
}
