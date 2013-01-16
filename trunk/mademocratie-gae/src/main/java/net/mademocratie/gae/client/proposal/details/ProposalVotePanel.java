package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.client.citizen.RegisterPage;
import net.mademocratie.gae.client.proposal.ProposalPage;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.exception.AnonymousCantVoteException;
import net.mademocratie.gae.server.service.IManageVote;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
    private ProposalPage proposalPage;

    public ProposalVotePanel(String proposalVoteId, ProposalPage proposalPage, Long proposalId, IManageVote manageVote) {
        super(proposalVoteId);
        LOGGER.info("proposalVote proposalVoteId=" + proposalVoteId + " proposalId=" + proposalId);
        this.manageVote = manageVote;
        this.proposalId = proposalId;
        this.proposalPage = proposalPage;
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
            public void onClick(AjaxRequestTarget target) {
                clickVote(target, VoteKind.PRO);
            }
        };
        neutralButton = new ProposalVoteButton("proposalVoteNeutralButton", VoteKind.NEUTRAL){
            @Override
            public void onClick(AjaxRequestTarget target) {
                clickVote(target, VoteKind.NEUTRAL);
            }
        };
        conButton = new ProposalVoteButton("proposalVoteConButton", VoteKind.CON){
            @Override
            public void onClick(AjaxRequestTarget target) {
                clickVote(target, VoteKind.CON);
            }
        };

        toggleVote(currentVote != null ? currentVote.getKind() : null);

        add(proButton);
        add(neutralButton);
        add(conButton);
    }

    private void clickVote(AjaxRequestTarget target, VoteKind voteKind) {
        try {
            vote(target, voteKind);
            proposalPage.updateVoteCount();
            target.add(proposalPage);
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

    private void vote(AjaxRequestTarget target, VoteKind voteKind) throws AnonymousCantVoteException {
        Citizen currentUser = CitizenSession.get().getCitizen();
        if (currentUser == null) {
            LOGGER.info("anonymous can't vote");
            throw new AnonymousCantVoteException();
        }
        manageVote.vote(currentUser.getEmail(),proposalId,voteKind);
        toggleVote(voteKind);
    }
}
