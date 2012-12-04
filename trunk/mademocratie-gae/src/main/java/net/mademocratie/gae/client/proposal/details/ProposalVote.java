package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Vote;
import net.mademocratie.gae.model.VoteKind;
import net.mademocratie.gae.server.CitizenSession;
import net.mademocratie.gae.server.service.IManageVote;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.logging.Logger;

/**
 * ProposalVote
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVote extends Panel {
    private final static Logger LOGGER = Logger.getLogger(ProposalVote.class.getName());

    private IManageVote manageVote;

    private ProposalVoteButton proButton;
    private ProposalVoteButton neutralButton;
    private ProposalVoteButton conButton;

    private Vote currentVote;

    public ProposalVote(String proposalVoteId, Long proposalId, IManageVote manageVote) {
        super(proposalVoteId);
        this.manageVote = manageVote;
        initData(proposalId);
        initComponents();
    }

    private void initData(Long proposalId) {
        Citizen currentUser = CitizenSession.get().getCitizen();
        if (currentUser == null) {
            return;
        }
        currentVote = manageVote.getProposalVoteOfACitizen(currentUser.getEmail(), proposalId);
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
                toggleVote(VoteKind.PRO);
            }
        };
        neutralButton = new ProposalVoteButton("proposalVoteNeutralButton", VoteKind.NEUTRAL){
            @Override
            public void onClick() {
                toggleVote(VoteKind.NEUTRAL);
            }
        };
        conButton = new ProposalVoteButton("proposalVoteConButton", VoteKind.CON){
            @Override
            public void onClick() {
                toggleVote(VoteKind.CON);
            }
        };

        toggleVote(currentVote != null ? currentVote.getKind() : null);

        add(proButton);
        add(neutralButton);
        add(conButton);
    }
}
