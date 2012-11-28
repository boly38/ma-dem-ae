package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Vote;

import javax.jdo.PersistenceManager;

public class JdoVoteRepository extends JdoRepository<Vote> {
    @Inject
    public JdoVoteRepository(Provider<PersistenceManager> pmProvider)
    {
        super(Vote.class, pmProvider);
    }
}
