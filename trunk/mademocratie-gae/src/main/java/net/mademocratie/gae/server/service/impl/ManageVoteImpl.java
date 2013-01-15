package net.mademocratie.gae.server.service.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.ProposalVotes;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
import net.mademocratie.gae.server.service.IManageVote;
import net.mademocratie.gae.server.service.IProposal;
import net.mademocratie.gae.server.service.IRepository;
import net.mademocratie.gae.server.service.IVote;

import java.util.List;
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
    public Vote getProposalVoteOfACitizen(String citizenEmail, Long proposalId) {
        return votesQueries.findProposalVoteByUserEmail(citizenEmail, proposalId);
    }

    @Override
    public Vote vote(String citizenEmail, Long proposalId, VoteKind kind) {
        Vote existingVote = votesQueries.findProposalVoteByUserEmail(citizenEmail, proposalId);
        if (existingVote != null) {
            votesQueries.removeVoteByUserEmail(citizenEmail, proposalId);
        }
        Vote vote = new Vote(citizenEmail, proposalId, kind);
        voteRepo.persist(vote);
        LOGGER.info("* Vote ADDED : " + vote);
        return vote;
    }

    @Override
    public ProposalVotes getProposalVotes(Long proposalId) {
        return new ProposalVotes(votesQueries.findProposalVotes(proposalId));
    }

    @Override
    public void removeProposalVotes(Long proposalId) {
       votesQueries.removeProposalVotes(proposalId);
    }

    @Override
    public List<Vote> latest(int max) {
        return votesQueries.latest(max);
    }
}
