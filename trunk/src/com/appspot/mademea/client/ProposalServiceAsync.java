package com.appspot.mademea.client;

import java.util.List;

import com.appspot.mademea.shared.ProposalProxy;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>ProposalService</code>.
 */
public interface ProposalServiceAsync {

	void addProposal(String title, String description, AsyncCallback<Void> callback);
	

	void getProposals(AsyncCallback<List<ProposalProxy>> callback);

}
