package net.mademocratie.gae.server.jdo;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

import javax.jdo.PersistenceManager;

/**
 * DataStoreGuiceModule
 *
 * This module binds the JDO {@link javax.jdo.PersistenceManager} interface to the provider that allows the
 * implementation to be injected as Provider&lt;PersistenceManager&gt;.
 *
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class DataStoreGuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PersistenceManager.class).toProvider(new Provider<PersistenceManager>()
        {
            public PersistenceManager get()
            {
                return DataStore.getPersistenceManager();
            }
        });
    }
}
