package com.appspot.mademocratie.server.service;

import javax.jdo.PersistenceManager;

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.jdo.JdoRepository;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class JdoProposalRepository extends JdoRepository<Proposal> {
    @Inject
    public JdoProposalRepository(Provider<PersistenceManager> pmProvider)
    {
        super(Proposal.class, pmProvider);
    }
}
