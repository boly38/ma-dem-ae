package net.mademocratie.gae.server.service.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IProposal;
import net.mademocratie.gae.server.service.IRepository;
import net.mademocratie.gae.server.service.IVote;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ManageProposalImpl implements IManageProposal {
    private final static Logger LOGGER = Logger.getLogger(ManageProposalImpl.class.getName());
    // ~services
    @Inject
    private IProposal proposalsQueries;
    @Inject
    private IVote votesQueries;
    @Inject
    private IRepository<Proposal> proposalRepo;

    @Override
    public Proposal getById(Long proposalId) {
        return proposalRepo.get(proposalId);
    }

	@Override
	public void addProposal(Proposal inputProposal, Citizen author) {
        inputProposal.setAuthor(author);
        inputProposal.setDate(new Date());
       	proposalRepo.persist(inputProposal);
        LOGGER.info("* Proposal ADDED : " + inputProposal);
    }

	@Override
	public List<Proposal> latest(int max) {
		return proposalsQueries.latest(max);
	}

    @Override
    public void removeAll() {
        proposalsQueries.removeAll();
        votesQueries.removeAll();
    }

    public void setProposalsQueries(IProposal proposalsQueries) {
        this.proposalsQueries = proposalsQueries;
    }

    public void setProposalRepo(IRepository<Proposal> proposalRepo) {
        this.proposalRepo = proposalRepo;
    }
}
