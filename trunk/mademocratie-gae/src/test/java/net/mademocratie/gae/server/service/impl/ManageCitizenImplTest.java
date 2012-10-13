package net.mademocratie.gae.server.service.impl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import junit.framework.Assert;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.GuiceModule;
import net.mademocratie.gae.server.service.IManageCitizen;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ManageCitizenImplTest {
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ManageCitizenImplTest.class.getName());

    @Mock UserService userService ;

    @InjectMocks ManageCitizenImpl manageCitizen = new ManageCitizenImpl();

    private final String USER_EMAIL = "toto@yoyo.fr";
    private final String USER_DOMAIN = "yoyo.fr";
    private final String USER_PSEUDO = "toto";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        User user = new User(USER_EMAIL, USER_DOMAIN, USER_PSEUDO);
        when(userService.getCurrentUser()).thenReturn(user);
    }

    @Test
    public void testSuggestCitizen() {
        Citizen suggestCitizen = manageCitizen.suggestCitizen();
        assertNotNull("suggestCitizen is null", suggestCitizen);
        logger.info("suggestCitizen result " + suggestCitizen.toString());
        assertNotNull("suggestCitizen don't have pseudo", suggestCitizen.getPseudo());
        assertNotNull("suggestCitizen don't have email", suggestCitizen.getEmail());
        assertEquals("pseudo doesn't match", USER_PSEUDO, suggestCitizen.getPseudo());
        assertEquals("email doesn't match", USER_EMAIL, suggestCitizen.getEmail());

    }
}
