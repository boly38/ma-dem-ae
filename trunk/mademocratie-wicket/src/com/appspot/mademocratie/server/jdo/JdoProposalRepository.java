package com.appspot.mademocratie.server.jdo;

import javax.jdo.PersistenceManager;

import com.appspot.mademocratie.model.Proposal;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JdoProposalRepository extends JdoRepository<Proposal> {
    @Inject
    public JdoProposalRepository(Provider<PersistenceManager> pmProvider)
    {
        super(Proposal.class, pmProvider);
    }
}
