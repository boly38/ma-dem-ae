package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.model.ProposalVotes;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.logging.Logger;

/**
 * ProposalVoteSummaryPanel
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVoteSummaryPanel extends Panel {
    private static final Logger LOGGER = Logger.getLogger(ProposalVotePanel.class.getName());
    private ProposalVotes votesSummary;

    public ProposalVoteSummaryPanel(String id, ProposalVotes votesSummary) {
        super(id);
        this.votesSummary = votesSummary;
        initComponents();
    }

    private void initComponents() {
        Label voteForCountLabel = new Label("proposalPro", String.valueOf(votesSummary.voteProCount()));
        Label voteNeutralCountLabel = new Label("proposalNeutral", String.valueOf(votesSummary.voteNeutralCount()));
        Label voteConCountLabel = new Label("proposalCon", String.valueOf(votesSummary.voteConCount()));

        add(voteForCountLabel);
        add(voteNeutralCountLabel);
        add(voteConCountLabel);

    }

}
