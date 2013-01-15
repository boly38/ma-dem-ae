package net.mademocratie.gae.server.jdo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.service.ICitizen;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.List;
import java.util.logging.Logger;

public class JdoCitizenQueries extends JdoQueries<Citizen> implements ICitizen {
    private final static Logger LOGGER = Logger.getLogger(JdoCitizenQueries.class.getName());

    @Inject
    public JdoCitizenQueries(Provider<PersistenceManager> pmProvider)
    {
        super(Citizen.class, pmProvider);
    }

    /**
     * Select a citizen providing his email
     * src: https://developers.google.com/appengine/docs/java/datastore/jdo/queries
     * @param emailParam
     * @return Citizen if found else null
     */
    @Override
    public Citizen findByEmail(String emailParam) {
        Query query = newQuery();
        query.setFilter("email == emailParam");
        query.declareParameters("String emailParam");
        List<Citizen> citizens = null;
        try {
             citizens = toList(query.execute(emailParam));
        } finally {
            query.closeAll();
        }
        LOGGER.info("findByEmail " + emailParam + " result count=" + (citizens != null ? citizens.size() : "0"));
        return (citizens != null && citizens.size() > 0 ? citizens.get(0) : null);
    }

    @Override
    public List<Citizen> latest(int max)
    {
        Query query = newQuery();
        query.setOrdering(Citizen.CITIZEN_DATE + " desc");
        query.setRange(0, max);
        return toList(query.execute());
    }

    @Override
    public void removeAll() {
        Query query = newQuery();
        long nbDelete = query.deletePersistentAll();
        LOGGER.info("removeAll delete count=" + nbDelete);
    }
}