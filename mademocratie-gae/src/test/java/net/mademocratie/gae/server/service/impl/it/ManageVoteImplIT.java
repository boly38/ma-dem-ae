package net.mademocratie.gae.server.service.impl.it;

import net.mademocratie.gae.server.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

/**
 * ManageVoteImplIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
@Ignore("DevInProgress")
public class ManageVoteImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageVoteImplIT.class.getName());

    private ManageProposalImpl manageProposal;
    private ManageVoteImpl manageVote;

    @Before
    public void init() {

    }

    /**
     * @throws Exception
     *
    @Test
    public void testAddProposalsWithAuthors() throws Exception {
        Citizen myAuthorA = new Citizen("jo la frite", "frite365", "frite@jo-la.fr", "abc123");
        Citizen myAuthorB = new Citizen("ji la frote", "frite421", "frote@jo-la.fr", "abc123");
        Proposal testProposalAnon = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal testProposalA = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal testProposalB = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        Proposal testProposalA2 = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        manageProposal.addProposal(testProposalAnon, null);
        manageProposal.addProposal(testProposalA, myAuthorA);
        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);
        logger.info("last addProposal result " + testProposalA2.toString());
        assertNotNull("last created proposal is null", testProposalA2);
        assertNotNull("last created proposal don't have id", testProposalA2.getId());
        assertEquals("last created proposal title has been updated", PROPOSAL_TITLE, testProposalA2.getTitle());
        assertEquals("last created proposal content has been updated", PROPOSAL_CONTENT, testProposalA2.getContent());
        assertNull(manageProposal.getById(testProposalAnon.getId()).getAuthorEmail());
        assertEquals(myAuthorA.getEmail(), manageProposal.getById(testProposalA.getId()).getAuthorEmail());
        assertEquals(myAuthorB.getEmail(), manageProposal.getById(testProposalB.getId()).getAuthorEmail());
        assertEquals(myAuthorA.getEmail(), manageProposal.getById(testProposalA2.getId()).getAuthorEmail());
    }
      */

}
