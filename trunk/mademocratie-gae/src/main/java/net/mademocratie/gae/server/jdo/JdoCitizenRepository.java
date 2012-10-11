package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Citizen;

import javax.jdo.PersistenceManager;

public class JdoCitizenRepository extends JdoRepository<Citizen> {
    @Inject
    public JdoCitizenRepository(Provider<PersistenceManager> pmProvider)
    {
        super(Citizen.class, pmProvider);
    }
}
