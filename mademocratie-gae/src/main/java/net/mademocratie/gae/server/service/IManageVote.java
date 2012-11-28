package net.mademocratie.gae.server.service;

import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;

/**
 * IManageVote
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public interface IManageVote {

    Vote getMyProposalVote(Long proposalId);

    void vote(Long proposalId, VoteKind kind);
}
