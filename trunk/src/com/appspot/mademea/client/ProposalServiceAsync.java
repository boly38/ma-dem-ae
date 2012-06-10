package com.appspot.mademea.client;

import java.util.Collection;

import com.appspot.mademea.client.domain.Proposal;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ProposalService</code>.
 */
public interface ProposalServiceAsync {

	void addProposal(Proposal newProposal, AsyncCallback<Void> callback);
	

	void getProposals(AsyncCallback<Collection<Proposal>> callback);

}
