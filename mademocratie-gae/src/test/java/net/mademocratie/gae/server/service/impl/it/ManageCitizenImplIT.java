package net.mademocratie.gae.server.service.impl.it;

import com.google.appengine.api.users.User;
import com.google.inject.Inject;
import junit.framework.Assert;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.exception.RegisterFailedException;
import net.mademocratie.gae.server.jdo.JdoCitizenQueries;
import net.mademocratie.gae.server.jdo.JdoCitizenRepository;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
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
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageCitizenImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageCitizenImplIT.class.getName());
    @Inject
    private ManageCitizenImpl manageCitizen;

    @Inject
    private JdoCitizenRepository citizenRepo;

    @Inject
    private JdoCitizenQueries citizenQueries;

    private String testUserMail = "boly38@gmail.com";
    private User testGoogleUser = new User(testUserMail, "gmail.com");

    @Before
    public void setUp() {
        super.setUp();
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
        this.helper.setEnvEmail(testUserMail);
        this.helper.setEnvIsAdmin(true);
        assertNotNull("could not authenticate a true user ?", manageCitizen.authenticateCitizen(testUserMail, null));
        assertTrue("unable to sign in google user", manageCitizen.signInGoogleCitizen());

        // TODO : implement wicket test pre-requisites
        Citizen curCitizen = CitizenSession.get().getCitizen();
        assertNotNull("no current citizen in the session ?", curCitizen);
        assertEquals("current session user is wrong", testUserMail, curCitizen.getEmail());
    }



}
