package net.mademocratie.gae.server.service;

import com.google.inject.ImplementedBy;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;

@ImplementedBy(ManageVoteImpl.class)
public interface IManageVote {

    Vote getProposalVoteOfACitizen(String citizenMail, Long proposalId);

    Vote vote(String citizenEmail, Long proposalId, VoteKind kind);
}
