package com.appspot.mademocratie.server;

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.jdo.JdoProposalQueries;
import com.appspot.mademocratie.server.jdo.JdoProposalRepository;
import com.appspot.mademocratie.server.jdo.PersistenceManagerFilter;
import com.appspot.mademocratie.server.service.IManageProposal;
import com.appspot.mademocratie.server.service.IProposal;
import com.appspot.mademocratie.server.service.IRepository;
import com.appspot.mademocratie.server.service.impl.ManageProposalImpl;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure()
    {
        // Enable per-request-thread PersistenceManager injection.
        install(new PersistenceManagerFilter.GuiceModule());

        // Business object bindings go here.
        bind(IManageProposal.class).to(ManageProposalImpl.class);
        bind(IProposal.class).to(JdoProposalQueries.class);
        bind(new TypeLiteral<IRepository<Proposal>>() { }).to(JdoProposalRepository.class);
    }
}
