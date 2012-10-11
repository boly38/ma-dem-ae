package net.mademocratie.gae.server.service.impl;

import junit.framework.Assert;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.GuiceModule;
import net.mademocratie.gae.server.jdo.JdoCitizenQueries;
import net.mademocratie.gae.server.jdo.JdoCitizenRepository;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

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

    private static final String USERNAME = "test_charly";

    @Before
    public void setUp() {
        super.setUp();
        JdoCitizenRepository citizenRepo = new JdoCitizenRepository(pmProvider);
        JdoCitizenQueries citizenQueries= new JdoCitizenQueries(pmProvider);
        // todo : find a way to auto-inject pm into repo & queries & remove this setters :'(
        this.manageCitizen = new ManageCitizenImpl();
        manageCitizen.setCitizenRepo(citizenRepo);
        manageCitizen.setCitizensQueries(citizenQueries);
    }

    @After
    public void tearDown() {
        super.tearDown();
        manageCitizen.removeAll();
    }



    @Test
    public void testSuggestCitizen() {
        Citizen suggestCitizen = manageCitizen.suggestCitizen();
        logger.info("suggestCitizen result " + suggestCitizen.toString());
        Assert.assertNotNull("suggestCitizen is null", suggestCitizen);
        Assert.assertNotNull("suggestCitizen don't have pseudo", suggestCitizen.getPseudo());
    }
}
