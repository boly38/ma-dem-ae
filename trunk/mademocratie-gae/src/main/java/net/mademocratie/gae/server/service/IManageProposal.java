package net.mademocratie.gae.server.service;

import java.util.List;

import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
import com.google.inject.ImplementedBy;

@ImplementedBy(ManageProposalImpl.class)
public interface IManageProposal {

    /**
     * add a new proposal to the database
     * @param inputProposal
     */
	public void addProposal(Proposal inputProposal);

    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);	
}
