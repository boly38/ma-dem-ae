package net.mademocratie.gae.server.service.impl.it;

import com.google.inject.Inject;
import net.mademocratie.gae.model.*;
import net.mademocratie.gae.server.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.exception.CitizenAlreadyExistsException;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.service.impl.ManageContributionsImpl;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

/**
 * ManageContributionsImplIT
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageContributionsImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageContributionsImplIT.class.getName());
    @Inject
    private ManageCitizenImpl manageCitizen;
    @Inject
    private ManageProposalImpl manageProposal;
    @Inject
    private ManageVoteImpl manageVote;
    @Inject
    private ManageContributionsImpl manageContributions;

    private static final String PROPOSAL_TITLE = "test_contribution";
    private static final String PROPOSAL_CONTENT = "test_contribution_content";
    private Citizen myAuthorA;
    private Citizen myAuthorB;
    private Proposal testProposalAnon;
    private Proposal testProposalA;
    private Proposal testProposalB;
    private Proposal testProposalA2;
    private int contributionsCount;
    private Vote bForANeutral;
    private Vote bForAPro;
    private VoteContribution contribution_bForAPro;
    private Proposal testProposalAnonB;

    @Before
    public void init() throws CitizenAlreadyExistsException {
        super.setUp();
        manageProposal.removeAll();
        manageCitizen.removeAll();
        myAuthorA = manageCitizen.addCitizen(new Citizen("jo la frite", "frite365", "friteA@jo-la.fr", "abc123"));
        myAuthorB = manageCitizen.addCitizen(new Citizen("ji la frote", "frite421", "froteB@jo-la.fr", "abc123"));

        testProposalAnon = new Proposal(PROPOSAL_TITLE + "Anon", PROPOSAL_CONTENT);
        testProposalAnonB = new Proposal(PROPOSAL_TITLE + "AnonB", PROPOSAL_CONTENT);
        testProposalA = new Proposal(PROPOSAL_TITLE + "A", PROPOSAL_CONTENT);
        testProposalB = new Proposal(PROPOSAL_TITLE + "B", PROPOSAL_CONTENT);
        testProposalA2 = new Proposal(PROPOSAL_TITLE + "A2", PROPOSAL_CONTENT);

        manageProposal.addProposal(testProposalAnon, null);
        manageProposal.addProposal(testProposalAnonB, null);
        manageProposal.addProposal(testProposalA, myAuthorA);

        bForANeutral = manageVote.vote(myAuthorB.getEmail(), testProposalA.getId(), VoteKind.NEUTRAL);
        bForAPro = manageVote.vote(myAuthorB.getEmail(), testProposalA.getId(), VoteKind.PRO);

        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);

        contributionsCount = 7;
    }

    @Test
    public void testGetLastContributions() {
        // GIVEN
        int askedMaxContributions = 5;
        // WHEN
        List<IContribution> lastContributions = manageContributions.getLastContributions(askedMaxContributions);
        // THEN
        assertThat(lastContributions)
                .isNotNull().isNotEmpty();
        assertThat(lastContributions)
                .hasSize(Math.min(contributionsCount, askedMaxContributions));
        assertThat(lastContributions)
                .contains(testProposalA2);
        assertThat(lastContributions)
                .contains(testProposalB);
        assertThat(lastContributions)
                .contains(testProposalA);
        assertThat(lastContributions)
                .contains(testProposalAnonB);

        logger.info(lastContributions.toString());
        for (IContribution contribution : lastContributions) {
            logger.info("/CONTRIBUTION/ '" + contribution.getContributionDetails()
                   +"' on '" + contribution.getDate().toString()
                   +"' accessible using " + contribution.getContributionPage().getSimpleName());
        }

    }

    @After
    public void tearDown() {
        super.tearDown();
    }
}
