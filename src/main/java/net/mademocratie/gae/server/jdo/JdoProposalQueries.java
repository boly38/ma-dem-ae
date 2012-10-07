package net.mademocratie.gae.server.jdo;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IProposal;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JdoProposalQueries extends JdoQueries<Proposal> implements IProposal
{
    @Inject
    public JdoProposalQueries(Provider<PersistenceManager> pmProvider)
    {
        super(Proposal.class, pmProvider);
    }

    @Override
    public List<Proposal> latest(int max)
    {
        Query query = newQuery();
        query.setOrdering("date desc");
        query.setRange(0, max);
        return toList(query.execute());
    }
}