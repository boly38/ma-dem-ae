package net.mademocratie.gae.server.service;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;

import java.util.List;

@ImplementedBy(ManageProposalImpl.class)
public interface IManageProposal {

    /**
     * add a new proposal to the database
     * @param inputProposal
     */
	public void addProposal(Proposal inputProposal, Citizen author);

    /**
     * Return the latest proposals, ordered by descending date.
     *
     * @param max the maximum number of proposals to return
     * @return the proposals
     */
    List<Proposal> latest(int max);

    /**
     * remove all proposals from the repository (test usage only)
     */
    void removeAll();

    Proposal getById(Long proposalId);
}
