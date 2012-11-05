package net.mademocratie.gae.server.service.impl;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.service.ICitizen;
import net.mademocratie.gae.server.service.IRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ManageCitizenImplTest {
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(ManageCitizenImplTest.class.getName());

    @Mock UserService userService;

    @SuppressWarnings({"UnusedDeclaration"})
    @Mock private ICitizen citizensQueries;

    @SuppressWarnings({"UnusedDeclaration"})
    @Mock private IRepository<Citizen> citizenIRepository;

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
    public void testLatest() {
        manageCitizen.latest(100);
        verify(citizensQueries).latest(100);
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

    @Test
    public void testAddCitizen() throws CitizenAlreadyExistsException {
        Citizen inputCitizen = new Citizen(USER_PSEUDO, null, "toto", USER_EMAIL, "location");
        manageCitizen.addCitizen(inputCitizen);
        verify(citizenIRepository).persist(inputCitizen);
    }

    @Test(expected = CitizenAlreadyExistsException.class)
    public void testAddCitizenThatAlReadyExists() throws CitizenAlreadyExistsException {
        when(citizensQueries.findByEmail(USER_EMAIL)).thenReturn(null);

        Citizen inputCitizen = new Citizen(USER_PSEUDO, null, "toto", USER_EMAIL, "location");
        manageCitizen.addCitizen(inputCitizen);
        verify(citizenIRepository).persist(inputCitizen);
        when(citizensQueries.findByEmail(USER_EMAIL)).thenReturn(inputCitizen);

        manageCitizen.addCitizen(inputCitizen);
    }
}
