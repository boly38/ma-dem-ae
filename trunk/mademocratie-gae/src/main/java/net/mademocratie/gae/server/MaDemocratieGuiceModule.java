package net.mademocratie.gae.server;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.server.jdo.*;
import net.mademocratie.gae.server.service.*;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;

public class MaDemocratieGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        System.out.println("My MaDemocratieGuiceModule configure");
        // Enable per-request-thread PersistenceManager injection.
        install(new DataStoreGuiceModule());

        // Business object bindings go here.

        // citizens
        bind(IManageCitizen.class).to(ManageCitizenImpl.class);
        bind(ICitizen.class).to(JdoCitizenQueries.class);
        bind(new TypeLiteral<IRepository<Citizen>>() { }).to(JdoCitizenRepository.class);

        // proposals
        bind(IManageProposal.class).to(ManageProposalImpl.class);
        bind(IProposal.class).to(JdoProposalQueries.class);
        bind(new TypeLiteral<IRepository<Proposal>>() { }).to(JdoProposalRepository.class);

        // votes
        bind(IManageVote.class).to(ManageVoteImpl.class);
        bind(IVote.class).to(JdoVoteQueries.class);
        bind(new TypeLiteral<IRepository<Vote>>() { }).to(JdoVoteRepository.class);
    }

}
