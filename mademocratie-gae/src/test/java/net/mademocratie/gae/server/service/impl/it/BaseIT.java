package net.mademocratie.gae.server.service.impl.it;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import net.mademocratie.gae.server.MaDemocratieApp;
import net.mademocratie.gae.server.jdo.DataStore;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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
    /*
    protected PersistenceManager pm;
    protected PersistenceManagerFactory pmf;
    protected Provider<PersistenceManager> pmProvider;
    *
    */

    /*
     * needed to inject UserServiceFactory.getUserService();
     * http://man.lesca.me/local/gae/appengine/docs/java/tools/localunittesting.html#Writing_Authentication_Tests
     */
    protected final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalUserServiceTestConfig())
                    .setEnvIsAdmin(true)
                    .setEnvIsLoggedIn(true)
                    .setEnvEmail("toto@yoyo.fr")
                    .setEnvAuthDomain("yoyo.fr");

    private WicketTester tester;

    @BeforeClass
    public static void globalSetup() {
        DataStore.initialize();
    }

    @Before
    public void setUp() {
        // google service
        helper.setUp();

        tester = new WicketTester(new MaDemocratieApp());
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @AfterClass
    public static void globalTearDown() {
        DataStore.finishRequest();
    }

}
