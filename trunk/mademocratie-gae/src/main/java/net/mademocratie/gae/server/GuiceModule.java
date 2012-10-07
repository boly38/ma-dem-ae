package net.mademocratie.gae.server;

import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.jdo.JdoProposalQueries;
import net.mademocratie.gae.server.jdo.JdoProposalRepository;
import net.mademocratie.gae.server.jdo.PersistenceManagerFilter;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IProposal;
import net.mademocratie.gae.server.service.IRepository;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
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
