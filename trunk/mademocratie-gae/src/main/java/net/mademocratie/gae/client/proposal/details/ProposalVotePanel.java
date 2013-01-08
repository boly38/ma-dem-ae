package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.client.citizen.RegisterPage;
import net.mademocratie.gae.client.common.VoteItemContainer;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.exception.AnonymousCantVoteException;
import net.mademocratie.gae.server.service.IManageVote;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.logging.Logger;

/**
 * ProposalVotePanel
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVotePanel extends Panel {
    private static final Logger LOGGER = Logger.getLogger(ProposalVotePanel.class.getName());

    private IManageVote manageVote;

    private ProposalVoteButton proButton;
    private ProposalVoteButton neutralButton;
    private ProposalVoteButton conButton;

    private Vote currentVote;
    private Long proposalId;
    private VoteItemContainer voteItemContainer;

    public ProposalVotePanel(String proposalVoteId, VoteItemContainer voteItemContainer, Long proposalId, IManageVote manageVote) {
        super(proposalVoteId);
        // LOGGER.finest("proposalVote");
        this.manageVote = manageVote;
        this.proposalId = proposalId;
        this.voteItemContainer = voteItemContainer;
        initComponents();
        initData();
    }

    private void initData() {
        Citizen currentUser = CitizenSession.get().getCitizen();
        if (currentUser == null) {
            return;
        }
        currentVote = manageVote.getProposalVoteOfACitizen(currentUser.getEmail(), proposalId);
        if (currentVote != null) {
            toggleVote(currentVote.getKind());
        }
    }

    private void toggleVote(VoteKind kind) {
        proButton.toggle(kind != null && kind.equals(VoteKind.PRO));
        neutralButton.toggle(kind != null && kind.equals(VoteKind.NEUTRAL));
        conButton.toggle(kind != null && kind.equals(VoteKind.CON));
    }

    /**
     * init 3 kind of vote buttons
     */
    private void initComponents() {
        proButton = new ProposalVoteButton("proposalVoteProButton", VoteKind.PRO) {
            @Override
            public void onClick() {
                clickVote(VoteKind.PRO);
            }
        };
        neutralButton = new ProposalVoteButton("proposalVoteNeutralButton", VoteKind.NEUTRAL){
            @Override
            public void onClick() {
                clickVote(VoteKind.NEUTRAL);
            }
        };
        conButton = new ProposalVoteButton("proposalVoteConButton", VoteKind.CON){
            @Override
            public void onClick() {
                clickVote(VoteKind.CON);
            }
        };

        toggleVote(currentVote != null ? currentVote.getKind() : null);

        add(proButton);
        add(neutralButton);
        add(conButton);
    }

    private void clickVote(VoteKind voteKind) {
        try {
            vote(voteKind);
            voteItemContainer.updateVoteCount();
        } catch (AnonymousCantVoteException e) {
            String errMsg = "anonymous user cant vote, please register or sign in!";
            LOGGER.info(errMsg);
            CitizenSession.get().error(errMsg);
            gotoRegister();
        }
    }

    private void gotoRegister() {
        // LOGGER.finest("goto register");
        setResponsePage(RegisterPage.class);
    }

    private void vote(VoteKind voteKind) throws AnonymousCantVoteException {
        Citizen currentUser = CitizenSession.get().getCitizen();
        if (currentUser == null) {
            throw new AnonymousCantVoteException();
        }
        manageVote.vote(currentUser.getEmail(),proposalId,voteKind);
        toggleVote(voteKind);
    }
}
