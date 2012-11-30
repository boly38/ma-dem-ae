package net.mademocratie.gae.server.service.impl.it;

import com.google.inject.Inject;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
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
        myAuthorA = new Citizen("jo la frite", "frite365", "frite@jo-la.fr", "abc123");
        myAuthorB = new Citizen("ji la frote", "frite421", "frote@jo-la.fr", "abc123");
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

}
