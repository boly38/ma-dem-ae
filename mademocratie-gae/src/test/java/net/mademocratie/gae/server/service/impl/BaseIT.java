package net.mademocratie.gae.server.service.impl;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.inject.Provider;
import org.junit.After;
import org.junit.Before;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

/**
 * BaseIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public abstract class BaseIT {
    // private static final ThreadLocal<PersistenceManager> tlPm = new ThreadLocal<PersistenceManager>();
    protected PersistenceManager pm;
    protected PersistenceManagerFactory pmf;
    protected Provider<PersistenceManager> pmProvider;

    /*
     * needed to inject UserServiceFactory.getUserService();
     * http://man.lesca.me/local/gae/appengine/docs/java/tools/localunittesting.html#Writing_Authentication_Tests
     */
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalUserServiceTestConfig())
                    .setEnvIsAdmin(true)
                    .setEnvIsLoggedIn(true)
                    .setEnvEmail("toto@yoyo.fr")
                    .setEnvAuthDomain("yoyo.fr");

    @Before
    public void setUp() {
        pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        pm = pmf.getPersistenceManager();
        // tlPm.set(pm);
        pmProvider = new Provider<PersistenceManager>()
        {
            @Override
            public PersistenceManager get()
            {
                return pm;
            }
        };
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
        pm.close();
    }

}
