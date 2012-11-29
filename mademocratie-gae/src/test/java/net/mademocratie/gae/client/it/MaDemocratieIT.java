package net.mademocratie.gae.client.it;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.google.inject.Provider;
import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.server.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.MaDemocratieApp;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

@Ignore("dev in progress")
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class MaDemocratieIT {
    protected PersistenceManager pm;
    protected PersistenceManagerFactory pmf;
    protected Provider<PersistenceManager> pmProvider;
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

    @Before
    public void init() {
        pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
        pm = pmf.getPersistenceManager();
        pmProvider = new Provider<PersistenceManager>()
        {
            @Override
            public PersistenceManager get()
            {
                return pm;
            }
        };
        helper.setUp();
        tester = new WicketTester(new MaDemocratieApp());
//      find the way to inject pm
//        tester.getApplication().getFilterFactoryManager().add(?) // new PersistenceManagerFilter()
    }

    @After
    public void tearDown() {
        helper.tearDown();
        pm.close();
    }

    @Test
    public void testHomePage() {
        tester.startPage(HomePage.class);
        tester.assertContains("Ma démocratie");
    }

}
