package net.mademocratie.gae.server.jdo;

import com.google.inject.Provider;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

/**
 * DataStore
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 * src : http://stackoverflow.com/questions/4185382/how-to-use-jdo-persistence-manager
 * @version : $Revision$
 */
public class DataStore {
    private static PersistenceManagerFactory PMF;
    private static final ThreadLocal<PersistenceManager> PER_THREAD_PM = new ThreadLocal<PersistenceManager>();
    public static void initialize() {
        if (PMF != null) {
            throw new IllegalStateException("initialize() already called");
        }
        PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
    }

    public static PersistenceManager getPersistenceManager() {
        PersistenceManager pm = PER_THREAD_PM.get();
        if (pm == null) {
            pm = PMF.getPersistenceManager();
            PER_THREAD_PM.set(pm);
        }
        return pm;
    }

    public static Provider<PersistenceManager> getPersistenceProvider() {
        return new Provider<PersistenceManager>() {
            public PersistenceManager get()
            {
                return DataStore.getPersistenceManager();
            }
        };
    }

    public static void finishRequest() {
        PersistenceManager pm = PER_THREAD_PM.get();
        if (pm != null) {
            PER_THREAD_PM.remove();
            Transaction tx = pm.currentTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }
}