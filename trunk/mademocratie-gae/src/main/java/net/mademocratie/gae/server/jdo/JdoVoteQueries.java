package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.server.service.IVote;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

public class JdoVoteQueries extends JdoQueries<Vote> implements IVote {
    private final static Logger LOGGER = Logger.getLogger(JdoVoteQueries.class.getName());

    @Inject
    public JdoVoteQueries(Provider<PersistenceManager> pmProvider)
    {
        super(Vote.class, pmProvider);
    }

    /*
    @Override
    public List<Proposal> latest(int max)
    {
        Query query = newQuery();
        query.setOrdering("date desc");
        query.setRange(0, max);
        return toList(query.execute());
    }
    */

    @Override
    public Vote findProposalVoteByUserEmail(String citizenEmail, Long proposalId) {
        if (citizenEmail == null) {
            return null;
        }
        Query query = newQuery();
        query.declareParameters("String emailParam, Long proposalIdParam");
        query.setFilter("citizenEmail == emailParam"
                      + "&& proposalId == proposalIdParam");
        List<Vote> votes = null;
        try {
            votes = toList(query.execute(citizenEmail, proposalId));
        } finally {
            query.closeAll();
        }
        LOGGER.info("findProposalVoteByUserEmail " + citizenEmail + " for proposalId=" + proposalId + " result count=" + (votes != null ? votes.size() : "0"));
        return (votes != null && votes.size() > 0 ? votes.get(0) : null);
    }

    @Override
    public void removeVoteByUserEmail(String citizenEmail, Long proposalId) {
        if (citizenEmail == null) {
            return;
        }
        Query query = newQuery();
        query.declareParameters("String emailParam, Long proposalIdParam");
        query.setFilter("citizenEmail == emailParam"
                      + "&& proposalId == proposalIdParam");
        long nbDelete = query.deletePersistentAll(citizenEmail, proposalId);
        LOGGER.info("remove vote by " + citizenEmail + " for proposal#" + proposalId + " delete count=" + nbDelete);
    }

    @Override
    public Collection<Vote> findProposalVotes(Long proposalId) {
        Query query = newQuery();
        query.declareParameters("Long proposalIdParam");
        query.setFilter("proposalId == proposalIdParam");
        List<Vote> votes = null;
        try {
            votes = toList(query.execute(proposalId));
        } finally {
            query.closeAll();
        }
        LOGGER.info("findProposalVotes for proposalId=" + proposalId + " result count=" + (votes != null ? votes.size() : "0"));
        return votes;
    }

    @Override
    public void removeProposalVotes(Long proposalId) {
        Query query = newQuery();
        query.declareParameters("Long proposalIdParam");
        query.setFilter("proposalId == proposalIdParam");
        long nbDelete = query.deletePersistentAll( proposalId);
        LOGGER.info("removeProposalVotes for proposal#" + proposalId + " delete count=" + nbDelete);
    }

    @Override
    public void removeAll() {
        Query query = newQuery();
        long nbDelete = query.deletePersistentAll();
        LOGGER.info("removeAll delete count=" + nbDelete);
    }


    @Override
    public List<Vote> latest(int max) {
        Query query = newQuery();
        query.setOrdering(Vote.VOTE_DATE + " desc");
        query.setRange(0, max);
        return toList(query.execute());
    }
}