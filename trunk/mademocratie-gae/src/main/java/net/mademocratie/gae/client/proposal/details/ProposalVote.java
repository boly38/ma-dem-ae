package net.mademocratie.gae.client.proposal.details;

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

    public ProposalVote(String id) {
        super(id);
        initComponents();
    }

    /**
     * class not selected :
     * btn btn-info
     *
     * class selected :
     * btn btn-warning
     */
    private void initComponents() {
        add(new ProposalVoteButton("proposalVoteProButton", "#", VoteKind.PRO));
        add(new ProposalVoteButton("proposalVoteNeutralButton", "#", VoteKind.NEUTRAL));
        add(new ProposalVoteButton("proposalVoteConButton", "#", VoteKind.CON));
    }
}
