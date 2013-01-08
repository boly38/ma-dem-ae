package net.mademocratie.gae.client.proposal;

import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.client.common.VoteItemContainer;
import net.mademocratie.gae.client.proposal.details.ProposalVotePanel;
import net.mademocratie.gae.client.proposal.details.ProposalVoteSummaryPanel;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.model.ProposalVotes;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IManageVote;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

import javax.inject.Inject;
import java.util.logging.Logger;

public class ProposalPage extends PageTemplate implements VoteItemContainer {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    private final static Logger LOGGER = Logger.getLogger(ProposalPage.class.getName());

    //~services
    @Inject
    private IManageVote manageVote;

    @Inject
    private IManageProposal manageProposal;

    //~design
    private Long propId;
    
    private Proposal proposal;
    private ProposalVotes proposalVotes;
    private Label proposalVotesCountLabel;
    private ProposalVoteSummaryPanel proposalVoteSummary;


    public ProposalPage() {
        super(null);
        handleParams();
        initComponents();
    }


    public ProposalPage(PageParameters params) {
        super(params);
        // LOGGER.finest("proposalPage");
        handleParams();
        loadData();
        initComponents();
    }


    private void handleParams() {
        PageParameters params = this.getPageParameters();
        StringValue propIdStr = (params != null ? params.get("id") : null);
        try {
            propId = (propIdStr != null ? propIdStr.toLong() : null);
        } catch (StringValueConversionException nfe) {
            // nothing
        }
        // LOGGER.finest("display proposal number " + propId);
    }

    private void loadData() {
        proposal = (propId != null ? manageProposal.getById(propId) : null);
        if (proposal == null) {
            getSession().error("Unable to retrieve the proposal");
            throw new RestartResponseException(HomePage.class, null);
        }
        loadDataProposalVotes();
    }

    private void initComponents() {
        createProposalDescription();
        createProposalVote();
    }

    public void updateVoteCount() {
        loadDataProposalVotes();
        proposalVotesCountLabel.setDefaultModel(new Model<String>(String.valueOf(proposalVotes.voteCount())));
        remove(proposalVoteSummary);
        proposalVoteSummary = new ProposalVoteSummaryPanel("proposalVoteSummary", proposalVotes);
        add(proposalVoteSummary);
    }

    private void loadDataProposalVotes() {
        proposalVotes = manageVote.getProposalVotes(propId);
    }


    private void createProposalVote() {
        add(new ProposalVotePanel("proposalVote", this, propId, manageVote));
        proposalVoteSummary = new ProposalVoteSummaryPanel("proposalVoteSummary", proposalVotes);
        add(proposalVoteSummary);
    }

    private void createProposalDescription() {
    	String author = proposal.getAuthorPseudo() != null ? proposal.getAuthorPseudo() : "An anonymous person ";
    	String pDescString = proposal.getContent();

        WebMarkupContainer pDescContainer = new WebMarkupContainer("pdesc-container");
        add(pDescContainer);
    	
    	Label proposalDate = new Label("proposalDate", proposal.getDate().toString());
        proposalVotesCountLabel = new Label("proposalVotesCount", new Model<String>(String.valueOf(proposalVotes.voteCount())));
    	Label proposalAuthor = new Label("proposalAuthor", author);
    	Label proposalTitle = new Label("proposalTitle", proposal.getTitle());
    	Label proposalDesc = new Label("proposalDescription", pDescString);
    	
    	add(proposalDate);
    	add(proposalAuthor);
        add(proposalVotesCountLabel);
    	add(proposalTitle);
    	pDescContainer.add(proposalDesc);
    	
    	pDescContainer.setVisible(pDescString != null);
	}
}