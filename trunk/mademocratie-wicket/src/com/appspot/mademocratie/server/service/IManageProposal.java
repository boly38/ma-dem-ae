package com.appspot.mademocratie.server.service;

import java.util.List;

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.impl.ManageProposalImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(ManageProposalImpl.class)
public interface IManageProposal {

	public void addProposal(Proposal inputProposal);

    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);	
}
