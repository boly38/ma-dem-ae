package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Vote;

import java.util.Collection;

public interface IVote {
    Vote findProposalVoteByUserEmail(String citizenEmail, Long proposalId);

    void removeVoteByUserEmail(String citizenEmail, Long proposalId);

    Collection<Vote> findProposalVotes(Long proposalId);
}
