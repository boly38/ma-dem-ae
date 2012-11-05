package net.mademocratie.gae.server.service.impl.it;

import junit.framework.Assert;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.GuiceModule;
import net.mademocratie.gae.server.jdo.JdoProposalQueries;
import net.mademocratie.gae.server.jdo.JdoProposalRepository;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * test sample : http://code.google.com/p/wicket-gae-template/source/browse/branches/experimental/scala/src/test/java/com/example/service/JdoGreetingPersistenceTest.java?r=29
 * @DevInProgress
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ GuiceModule.class })
public class ManageProposalImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageProposalImplIT.class.getName());
    private ManageProposalImpl manageProposal;

    private static final String PROPOSAL_TITLE = "test_proposal";
    private static final String PROPOSAL_CONTENT = "test_proposal";

    @Before
    public void setUp() {
        super.setUp();
        JdoProposalRepository proposalRepo = new JdoProposalRepository(pmProvider);
        JdoProposalQueries proposalQueries= new JdoProposalQueries(pmProvider);
        // todo : find a way to auto-inject pm into repo & queries & remove this setters :'(
        this.manageProposal = new ManageProposalImpl();
        manageProposal.setProposalRepo(proposalRepo);
        manageProposal.setProposalsQueries(proposalQueries);
    }

    @After
    public void tearDown() {
        super.tearDown();
        manageProposal.removeAll();
    }

    /**
     * @throws Exception
     */
    @Test
    public void testAddProposal() throws Exception {
        Proposal testProposal = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        logger.info("addProposal input " + testProposal.toString());
        manageProposal.addProposal(testProposal);
        logger.info("addProposal result " + testProposal.toString());
        Assert.assertNotNull("just created proposal is null", testProposal);
        Assert.assertNotNull("just created proposal don't have id", testProposal.getId());
        Assert.assertEquals("just created proposal title has been updated", PROPOSAL_TITLE, testProposal.getTitle());
        Assert.assertEquals("just created proposal content has been updated",PROPOSAL_CONTENT, testProposal.getContent());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testLatest() throws Exception {
        Proposal testProposal = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        manageProposal.addProposal(testProposal);
        List<Proposal> latestProposals = manageProposal.latest(10);
        Assert.assertNotNull("latest proposals List is null", latestProposals);
        int latestProposalsSize = latestProposals.size();
        String latestProposalsLogString = Arrays.toString(latestProposals.toArray());
        Assert.assertFalse("latest proposals List size " + latestProposalsSize + " < 1 : \n\t"
                + latestProposalsLogString, latestProposalsSize < 1);
        Assert.assertFalse("latest proposals List size " + latestProposalsSize + "> 10 : \n\t"
                + latestProposalsLogString, latestProposalsSize > 10);
    }
}