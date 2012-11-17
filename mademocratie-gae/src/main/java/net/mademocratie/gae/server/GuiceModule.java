package net.mademocratie.gae.server;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.jdo.*;
import net.mademocratie.gae.server.service.*;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure()
    {
        // Enable per-request-thread PersistenceManager injection.
        install(new PersistenceManagerFilter.GuiceModule());

        // Business object bindings go here.

        // proposals
        bind(IManageProposal.class).to(ManageProposalImpl.class);
        bind(IProposal.class).to(JdoProposalQueries.class);
        bind(new TypeLiteral<IRepository<Proposal>>() { }).to(JdoProposalRepository.class);

        // citizens
        bind(IManageCitizen.class).to(ManageCitizenImpl.class);
        bind(ICitizen.class).to(JdoCitizenQueries.class);
        bind(new TypeLiteral<IRepository<Citizen>>() { }).to(JdoCitizenRepository.class);

    }
}