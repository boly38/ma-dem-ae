package net.mademocratie.gae.server.service.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
import net.mademocratie.gae.server.service.IManageVote;
import net.mademocratie.gae.server.service.IProposal;
import net.mademocratie.gae.server.service.IRepository;
import net.mademocratie.gae.server.service.IVote;

import java.util.logging.Logger;

public class ManageVoteImpl implements IManageVote {
    private final static Logger LOGGER = Logger.getLogger(ManageVoteImpl.class.getName());
    // ~services
    @Inject
    private IProposal proposalsQueries;
    @Inject
    private IRepository<Proposal> proposalRepo;
    @Inject
    private IVote votesQueries;
    @Inject
    private IRepository<Vote> voteRepo;

    @Override
    public Vote getMyProposalVote(Long proposalId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Vote vote(String citizenEmail, Long proposalId, VoteKind kind) {
        //To change body of implemented methods use File | Settings | File Templates.
        Vote vote = new Vote(citizenEmail, kind, proposalId);
        LOGGER.info("add vote " + vote);
        voteRepo.persist(vote);
        return vote;
    }
}
