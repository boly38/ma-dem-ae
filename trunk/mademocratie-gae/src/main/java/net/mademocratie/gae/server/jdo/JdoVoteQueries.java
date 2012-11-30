package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.server.service.IVote;

import javax.jdo.PersistenceManager;

public class JdoVoteQueries extends JdoQueries<Vote> implements IVote
{
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
}