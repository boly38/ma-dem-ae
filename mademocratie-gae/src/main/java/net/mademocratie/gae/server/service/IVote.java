package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Vote;

public interface IVote {
    Vote findProposalVoteByUserEmail(String citizenEmail, Long proposalId);

    void removeVoteByUserEmail(String citizenEmail, Long proposalId);
}
