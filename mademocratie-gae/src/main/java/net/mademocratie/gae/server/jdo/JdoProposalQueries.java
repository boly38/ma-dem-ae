package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IProposal;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.logging.Logger;

public class JdoProposalQueries extends JdoQueries<Proposal> implements IProposal {
    private final static Logger LOGGER = Logger.getLogger(JdoProposalQueries.class.getName());

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

    @Override
    public void removeAll() {
        Query query = newQuery();
        long nbDelete = query.deletePersistentAll();
        LOGGER.info("removeAll delete count=" + nbDelete);
    }

}