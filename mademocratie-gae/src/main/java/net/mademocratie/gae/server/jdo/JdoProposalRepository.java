package net.mademocratie.gae.server.jdo;

import javax.jdo.PersistenceManager;

import net.mademocratie.gae.model.Proposal;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JdoProposalRepository extends JdoRepository<Proposal> {
    @Inject
    public JdoProposalRepository(Provider<PersistenceManager> pmProvider)
    {
        super(Proposal.class, pmProvider);
    }
}
