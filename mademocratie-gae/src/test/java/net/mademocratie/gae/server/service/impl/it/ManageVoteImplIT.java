package net.mademocratie.gae.server.service.impl.it;

import com.google.inject.Inject;
import net.mademocratie.gae.model.*;
import net.mademocratie.gae.server.MaDemocratieGuiceModule;
import net.mademocratie.gae.server.service.impl.ManageCitizenImpl;
import net.mademocratie.gae.server.service.impl.ManageProposalImpl;
import net.mademocratie.gae.server.service.impl.ManageVoteImpl;
import net.mademocratie.gae.test.GuiceJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({ MaDemocratieGuiceModule.class })
public class ManageVoteImplIT extends BaseIT {
    private static final Logger logger = Logger.getLogger(ManageVoteImplIT.class.getName());

    @Inject
    private ManageCitizenImpl manageCitizen;
    @Inject
    private ManageProposalImpl manageProposal;
    @Inject
    private ManageVoteImpl manageVote;


    private static final String PROPOSAL_TITLE = "test_proposal_vote";
    private static final String PROPOSAL_CONTENT = "test_proposal_vote";
    private Citizen myAuthorA;
    private Citizen myAuthorB;
    private Proposal testProposalAnon;
    private Proposal testProposalA;
    private Proposal testProposalB;
    private Proposal testProposalA2;

    @Before
    public void init() {
        super.setUp();
        myAuthorA = new Citizen("jo la frite", "frite365", "friteA@jo-la.fr", "abc123");
        myAuthorB = new Citizen("ji la frote", "frite421", "froteB@jo-la.fr", "abc123");
        testProposalAnon = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        testProposalA = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        testProposalB = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        testProposalA2 = new Proposal(PROPOSAL_TITLE, PROPOSAL_CONTENT);
        manageProposal.addProposal(testProposalAnon, null);
        manageProposal.addProposal(testProposalA, myAuthorA);
        manageProposal.addProposal(testProposalB, myAuthorB);
        manageProposal.addProposal(testProposalA2, myAuthorA);
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testAddProposalVoteConAnonymousProposal() throws Exception {
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalAnon.getId(), VoteKind.CON);
        assertThat(testVote.getId()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testAddProposalVoteProAnonymousProposal() throws Exception {
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalAnon.getId(), VoteKind.PRO);
        assertThat(testVote.getId()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testAddProposalVoteNeutralAuthoredProposal() throws Exception {
        Vote testVote = manageVote.vote(myAuthorB.getEmail(), testProposalA.getId(), VoteKind.NEUTRAL);
        assertThat(testVote.getId()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testAddProposalTwoDistinctVotesOnAuthoredProposal() throws Exception {
        Vote testVote = manageVote.vote(myAuthorB.getEmail(), testProposalA.getId(), VoteKind.NEUTRAL);
        Vote testVoteBis = manageVote.vote(myAuthorB.getEmail(), testProposalA.getId(), VoteKind.PRO);
        assertThat(testVoteBis.getId()).isNotNull();
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testGetProposalVote() throws Exception {
        // GIVEN
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalA.getId(), VoteKind.PRO);
        assertThat(testVote.getId()).isNotNull();
        // WHEN
        Vote retrievedVote = manageVote.getProposalVoteOfACitizen(myAuthorA.getEmail(), testProposalA.getId());
        // THEN
        assertThat(retrievedVote)
                .as("unable to retrieve a vote of a citizen")
                .isNotNull();
        assertThat(retrievedVote)
                .as("unable to retrieve the vote of the citizen")
                .isEqualTo(testVote);
    }

    /**
     * @throws Exception
     **/
    @Test
    public void testGetProposalVotes() throws Exception {
        // GIVEN
        Vote testVote = manageVote.vote(myAuthorA.getEmail(), testProposalA.getId(), VoteKind.PRO);
        Vote testVoteB = manageVote.vote(myAuthorB.getEmail(), testProposalA.getId(), VoteKind.NEUTRAL);
        assertThat(testVote.getId()).isNotNull();
        // WHEN
        ProposalVotes retrievedVotes = manageVote.getProposalVotes(testProposalA.getId());
        //THEN
        assertThat(retrievedVotes)
                .as("unable to retrieve a proposal's vote")
                .isNotNull();
        assertThat(retrievedVotes.getVotes())
                .as("unable to retrieve a proposal's vote")
                .isNotNull();
        assertThat(retrievedVotes.voteCount())
                .as("proposal's vote count is incorrect : " + retrievedVotes.voteCount())
                .isEqualTo(2);
        assertThat(retrievedVotes.getVotes())
                .as("unable to retrieve given proposal's vote")
                .contains(testVote, testVoteB);
    }

}
