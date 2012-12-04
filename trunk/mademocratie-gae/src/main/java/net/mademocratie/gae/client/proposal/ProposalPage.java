package net.mademocratie.gae.client.proposal;

import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.client.proposal.details.ProposalVote;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageProposal;
import net.mademocratie.gae.server.service.IManageVote;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

import javax.inject.Inject;
import java.util.logging.Logger;

public class ProposalPage extends PageTemplate {
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


    public ProposalPage() {
        super(null);
        handleParams();
        initComponents();
    }


    public ProposalPage(PageParameters params) {
        super(params);
        LOGGER.info("proposalPage");
        handleParams();
        initComponents();
    }

    private void initComponents() {
        createProposalDescription();
        createProposalVote();
    }

    private void handleParams() {
        PageParameters params = this.getPageParameters();
        StringValue propIdStr = (params != null ? params.get("id") : null);
        try {
            propId = (propIdStr != null ? propIdStr.toLong() : null);
        } catch (StringValueConversionException nfe) {
            // nothing
        }
        LOGGER.info("display proposal number " + propId);
        proposal = (propId != null ? manageProposal.getById(propId) : null);
        if (proposal == null) {
            getSession().error("Unable to retrieve the proposal");
            throw new RestartResponseException(HomePage.class, null);
        }
    }

    private void createProposalVote() {
        add(new ProposalVote("proposalVote", propId, manageVote));
    }

    private void createProposalDescription() {
    	String author = proposal.getAuthorPseudo() != null ? proposal.getAuthorPseudo() : "An anonymous person ";
    	String pDescString = proposal.getContent();

        WebMarkupContainer pDescContainer = new WebMarkupContainer("pdesc-container");
        add(pDescContainer);
    	
    	Label proposalDate = new Label("proposalDate", proposal.getDate().toString());
    	Label proposalAuthor = new Label("proposalAuthor", author);
    	Label proposalTitle = new Label("proposalTitle", proposal.getTitle());
    	Label proposalDesc = new Label("proposalDescription", pDescString);
    	
    	add(proposalDate);
    	add(proposalAuthor);
    	add(proposalTitle);
    	pDescContainer.add(proposalDesc);
    	
    	pDescContainer.setVisible(pDescString != null);
	}
}