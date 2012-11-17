package net.mademocratie.gae.server.service.impl.it;

import com.google.appengine.api.users.User;
import junit.framework.Assert;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.GuiceModule;
import net.mademocratie.gae.server.MaDemocratieApp;
import net.mademocratie.gae.server.exception.RegisterFailedException;
import net.mademocratie.gae.server.jdo.JdoCitizenQueries;
import net.mademocratie.gae.server.jdo.JdoCitizenRepository;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * ManageCitizenImplIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ GuiceModule.class })
public class ManageCitizenImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageCitizenImplIT.class.getName());
    private ManageCitizenImpl manageCitizen;

    private JdoCitizenRepository citizenRepo;
    private JdoCitizenQueries citizenQueries;
    private String testUserMail = "boly38@gmail.com";
    private User testGoogleUser = new User(testUserMail, "gmail.com");

    @Before
    public void setUp() {
        super.setUp();
        citizenRepo = new JdoCitizenRepository(pmProvider);
        citizenQueries= new JdoCitizenQueries(pmProvider);
        // todo : find a way to auto-inject pm into repo & queries & remove this setters :'(
        this.manageCitizen = new ManageCitizenImpl();
        manageCitizen.setCitizenRepo(citizenRepo);
        manageCitizen.setCitizensQueries(citizenQueries);
        cleanTestData();
    }

    private void cleanTestData() {
        Citizen testUser = citizenQueries.findByEmail(testUserMail);
        if (testUser != null) citizenRepo.delete(testUser);
    }

    @After
    public void tearDown() {
        super.tearDown();
    }



    @Test
    public void testSuggestCitizen() {
        Citizen suggestCitizen = manageCitizen.suggestCitizen();
        logger.info("suggestCitizen result " + suggestCitizen.toString());
        Assert.assertNotNull("suggestCitizen is null", suggestCitizen);
        Assert.assertNotNull("suggestCitizen don't have pseudo", suggestCitizen.getPseudo());
    }

    @Test
    public void testAuthenticateFakeCitizen() {
        assertNull("authenticate a fake user ?", manageCitizen.authenticateCitizen("test@yoyo.fr", "pass"));
    }

    @Test
    public void testAuthenticateAndSignInGoogleCitizen() throws RegisterFailedException {
        manageCitizen.register("boly38", testGoogleUser);
        assertNotNull("could not authenticate a true user ?", manageCitizen.authenticateCitizen(testUserMail, null));
        this.helper.setEnvEmail(testUserMail);
        this.helper.setEnvIsAdmin(true);
        assertTrue("unable to sign in google user", manageCitizen.signInGoogleCitizen());

        // TODO : implement wicket test pre-requisites
        Citizen curCitizen = CitizenSession.get().getCitizen();
        assertNotNull("no current citizen in the session ?", curCitizen);
        assertEquals("current session user is wrong", testUserMail, curCitizen.getEmail());
    }



}
