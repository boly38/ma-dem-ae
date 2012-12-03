package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.server.service.IVote;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
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
    public Vote findProposalVoteByUserEmail(String userEmail, Long proposalId) {
        Query query = newQuery();
        query.declareParameters("String emailParam, Long proposalIdParam");
        query.setFilter("citizenEmail == emailParam");
        query.setFilter("proposalId == proposalIdParam");
        List<Vote> votes = null;
        try {
            votes = toList(query.execute(userEmail, proposalId));
        } finally {
            query.closeAll();
        }
        LOGGER.info("findProposalVoteByUserEmail " + userEmail + " for proposalId=" + proposalId + " result count=" + (votes != null ? votes.size() : "0"));
        return (votes != null && votes.size() > 0 ? votes.get(0) : null);
    }
}