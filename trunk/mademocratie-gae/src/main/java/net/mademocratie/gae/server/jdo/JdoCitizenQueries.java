package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.service.ICitizen;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;

public class JdoCitizenQueries extends JdoQueries<Citizen> implements ICitizen
{
    @Inject
    public JdoCitizenQueries(Provider<PersistenceManager> pmProvider)
    {
        super(Citizen.class, pmProvider);
    }

    @Override
    public List<Citizen> latest(int max)
    {
        Query query = newQuery();
        query.setOrdering("date desc");
        query.setRange(0, max);
        return toList(query.execute());
    }
}