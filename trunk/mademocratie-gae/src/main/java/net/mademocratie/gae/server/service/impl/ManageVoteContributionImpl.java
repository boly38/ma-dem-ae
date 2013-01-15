package net.mademocratie.gae.server.service.impl;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteContribution;
import net.mademocratie.gae.server.service.*;

import java.util.*;

public class ManageVoteContributionImpl implements IManageVoteContributionImpl{
    // ~services
    @Inject
    private IProposal proposalsQueries;
    @Inject
    private IRepository<Proposal> proposalRepo;

    @Inject
    private IRepository<Citizen> citizenRepo;
    @Inject
    private ICitizen citizenQueries;

    @Inject
    private IVote votesQueries;
    @Inject
    private IRepository<Vote> voteRepo;



    @Override
    public List<VoteContribution> latest(int maxContributions) {
        List<Vote> lastVotes = votesQueries.latest(maxContributions);
        // collect dependent items to load
        Map<Long, Proposal> relatedProposalsCache = new HashMap<Long, Proposal>();
        Map<String, Citizen> relatedCitizensCache = new HashMap<String, Citizen>();
        List<VoteContribution> voteContributions = new ArrayList<VoteContribution>(lastVotes.size());
        for (Vote vote : lastVotes) {
            String citizenEmail = vote.getCitizenEmail();
            Long proposalId = vote.getProposalId();
            if (!relatedProposalsCache.containsKey(proposalId)) {
                relatedProposalsCache.put(proposalId, proposalRepo.get(proposalId));
            }
            if (!relatedCitizensCache.containsKey(citizenEmail)) {
                relatedCitizensCache.put(citizenEmail, citizenQueries.findByEmail(citizenEmail));
            }
            voteContributions.add(new VoteContribution(relatedCitizensCache.get(citizenEmail), relatedProposalsCache.get(proposalId), vote));
        }
        return voteContributions;
    }
}
