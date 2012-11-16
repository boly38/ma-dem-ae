package net.mademocratie.gae.client;

import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageProposal;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.logging.Logger;

public class ProposalsPage extends PageTemplate {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    private final static Logger LOGGER = Logger.getLogger(ProposalsPage.class.getName()); 
	//~design
	ProposalsPage page;
    
    private FeedbackPanel feedback;    
    // ~services
    @Inject
    private IManageProposal manageProposals;

    public ProposalsPage() {
    	super(null);
        initComponents();
        this.page = this;
    }
   
    public ProposalsPage(PageParameters params) {
        super(params);
        initComponents();
        this.page = this;
    }
	
    private void initComponents() {
	    createFeedback();
        createProposalsList();
    }
    
    private void createFeedback() {
	    feedback = new FeedbackPanel("feedback");
	    feedback.setOutputMarkupId(true);
	    add(feedback);    	
    }
    
	private void createProposalsList() {
        LoadableDetachableModel<List<Proposal>> latestProposals = new LoadableDetachableModel<List<Proposal>>() {
            /**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -1821066687751312009L;

			@Override
            protected List<Proposal> load()
            {
            	LOGGER.info("load proposals");
                return manageProposals.latest(500);
            }
        };

        ListView<Proposal> messages = new ListView<Proposal>("proposals", latestProposals) {
            /**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 2961302430978521634L;

			@Override
            protected void populateItem(ListItem<Proposal> item)
            {
                Proposal Proposal = item.getModel().getObject();
                String email = Proposal.getAuthor() != null ? Proposal.getAuthor().getPseudo() : "An anonymous person ";
                item.add(new Label("author", email));
                String proposalTitle = Proposal.getTitle();
                if (proposalTitle != null && proposalTitle.length() > 300) {
                	proposalTitle = proposalTitle.substring(0, 300).concat("...");
                }
                PageParameters params = new PageParameters();
                params.set("id", Proposal.getId());
                BookmarkablePageLink<ProposalPage> proposalDetailsLink = new BookmarkablePageLink<ProposalPage>("proposal", ProposalPage.class, params);
                item.add(proposalDetailsLink);
                Label proposalLabel = new Label("proposalLabel", proposalTitle);
                proposalDetailsLink.add(proposalLabel);                
            }
        };
        add(messages);		
	}
}