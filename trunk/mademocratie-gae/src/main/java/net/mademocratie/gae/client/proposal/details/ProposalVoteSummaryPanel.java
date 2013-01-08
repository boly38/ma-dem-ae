package net.mademocratie.gae.client.proposal.details;

import net.mademocratie.gae.model.ProposalVotes;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * ProposalVoteSummaryPanel
 * <p/>
 * Last update  : $LastChangedDate$
 * Last author  : $Author$
 *
 * @version : $Revision$
 */
public class ProposalVoteSummaryPanel extends Panel {
    // private static final Logger LOGGER = Logger.getLogger(ProposalVoteSummaryPanel.class.getName());
    private ProposalVotes votesSummary;
    private static final int MIN_VOTES_COUNT_TO_DISPLAY_SUMMARY = 4;

    public ProposalVoteSummaryPanel(String id, ProposalVotes votesSummary) {
        super(id);
        this.votesSummary = votesSummary;
        initComponents();
    }

    private void initComponents() {
        boolean voteSummaryVisible = votesSummary.voteCount() > MIN_VOTES_COUNT_TO_DISPLAY_SUMMARY;

        WebMarkupContainer specialVote = new WebMarkupContainer("special-vote");
        specialVote.setVisible(!voteSummaryVisible);
        add(specialVote);


        WebMarkupContainer voteSummaryContainer = new WebMarkupContainer("vote-summary");
        voteSummaryContainer.setVisible(voteSummaryVisible);

        Label voteForCountLabel = new Label("proposalPro", String.valueOf(votesSummary.voteProCount()));
        Label voteNeutralCountLabel = new Label("proposalNeutral", String.valueOf(votesSummary.voteNeutralCount()));
        Label voteConCountLabel = new Label("proposalCon", String.valueOf(votesSummary.voteConCount()));

        voteSummaryContainer.add(voteForCountLabel);
        voteSummaryContainer.add(voteNeutralCountLabel);
        voteSummaryContainer.add(voteConCountLabel);

        add(voteSummaryContainer);
    }

}
