package net.mademocratie.gae.server.jdo;

import com.google.inject.Provider;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * This base class supplies convenient methods for subclasses to implement JDO queries.
 * @param <T> the persistent entity type
 */
public abstract class JdoQueries<T> {
    private final static Logger LOGGER = Logger.getLogger(JdoQueries.class.getName()); 
    private final Class<T> clazz;
    private final Provider<PersistenceManager> pmProvider;

    protected JdoQueries(Class<T> clazz, Provider<PersistenceManager> pmProvider)
    {
    	//LOGGER.info("pmp=" + (pmProvider != null ? pmProvider.toString() : "null"));
        this.clazz = clazz;
        this.pmProvider = pmProvider;
    }

    protected Query newQuery()
    {
    	PersistenceManager pm = pmProvider.get();
    	if (pm == null) {
    		LOGGER.warning("No PersistenceManager declared (must be injected into web.xml conf file)");
    	}
        return pm.newQuery(clazz);
    }

    @SuppressWarnings("unchecked")
    protected Collection<T> toCollection(Object queryResult)
    {
        return (Collection<T>) queryResult;
    }

    protected List<T> toList(Object queryResult)
    {
        return new ArrayList<T>(toCollection(queryResult));
    }
}
