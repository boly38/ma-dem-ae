package net.mademocratie.gae.server.service.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IProposal;
import net.mademocratie.gae.server.service.IRepository;

import java.util.Date;
import java.util.List;

/**
 * @DevInProgress
 */
public class ManageProposalImpl implements IManageProposal {
    // ~services
    @Inject
    private IProposal proposalsQueries;
    @Inject
    private IRepository<Proposal> proposalRepo;

    
	@Override
	public void addProposal(Proposal inputProposal, Citizen author) {
    	inputProposal.setAuthor(author);
		inputProposal.setDate(new Date());
       	proposalRepo.persist(inputProposal);
		
	}

	@Override
	public List<Proposal> latest(int max) {
		return proposalsQueries.latest(max);
	}

    @Override
    public void removeAll() {
        // TODO : implement me
    }

    public void setProposalsQueries(IProposal proposalsQueries) {
        this.proposalsQueries = proposalsQueries;
    }

    public void setProposalRepo(IRepository<Proposal> proposalRepo) {
        this.proposalRepo = proposalRepo;
    }
}
