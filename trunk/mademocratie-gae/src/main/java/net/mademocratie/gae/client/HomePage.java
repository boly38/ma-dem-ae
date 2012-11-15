package net.mademocratie.gae.client;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageProposal;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;
import java.util.logging.Logger;

public class HomePage extends PageTemplate {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    private final static Logger LOGGER = Logger.getLogger(HomePage.class.getName()); 
	//~design
	HomePage page;
    
    private FeedbackPanel feedback;    
    // ~services
    @Inject
    private IManageProposal manageProposals;

    public HomePage() {
        super(new PageParameters());
        initComponents();
        this.page = this;
    }
    public HomePage(PageParameters params) {
        super(params);
        this.page = this;
        initComponents();
        initFeedback();
    }

    private void initFeedback() {
        if (getFeedbackSuccess() != null) {
            LOGGER.info("success:" + getFeedbackSuccess());
            success(getFeedbackSuccess());
            removeFeedbackSuccess();
        }
    }

    private void initComponents() {
	    createFeedback();
    	createHelloUser();
        createProposalsList();
    }
    
    private void createFeedback() {
	    feedback = new FeedbackPanel("feedback");
	    feedback.setOutputMarkupId(true);
	    add(feedback);    	
    }
   

    private void createHelloUser() {
        UserService userService = UserServiceFactory.getUserService();

        String boLink = "https://appengine.google.com/dashboard?&app_id=s~ma-dem-ae";
        if (getRequestUrl().contains("localhost")) {
        	boLink = "/_ah/admin";
        }
        
        WebMarkupContainer specialAdmin = new WebMarkupContainer("special-admin");
        add(specialAdmin);
        
    	ExternalLink linkBackOffice = new ExternalLink("link-backoffice", boLink);
    	specialAdmin.add(linkBackOffice);

       	specialAdmin.setVisible((userService.isUserLoggedIn() && userService.isUserAdmin()));
    }

	private void createProposalsList() {
		BookmarkablePageLink<ProposalsPage> proposalsLink
		  = new BookmarkablePageLink<ProposalsPage>("last-proposals", ProposalsPage.class, new PageParameters());
        add(proposalsLink);
        LoadableDetachableModel<List<Proposal>> latestProposals = new LoadableDetachableModel<List<Proposal>>() {
            /**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = -1821066687751312009L;

			@Override
            protected List<Proposal> load()
            {
            	LOGGER.info("load proposals");
                return manageProposals.latest(5);
            }
        };

//        if (!latestProposals.getObject().isEmpty())
//        {
//            noMessages.setVisible(false);
//        }

        ListView<Proposal> messages = new ListView<Proposal>("proposals", latestProposals) {
            /**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 2961302430978521634L;

			@Override
            protected void populateItem(ListItem<Proposal> item)
            {
                Proposal Proposal = item.getModel().getObject();
                String email = Proposal.getAuthor() != null ? Proposal.getAuthor().getNickname() : "An anonymous person ";
                item.add(new Label("author", email));
                String proposalTitle = Proposal.getTitle();
                if (proposalTitle != null && proposalTitle.length() > 30) {
                	proposalTitle = proposalTitle.substring(0, 30).concat("...");
                }
                PageParameters params = new PageParameters();
                params.set("id", Proposal.getId());
                BookmarkablePageLink<ProposalPage> proposalDetailsLink
                  = new BookmarkablePageLink<ProposalPage>("proposal", ProposalPage.class, params);
                item.add(proposalDetailsLink);
                Label proposalLabel = new Label("proposalLabel", proposalTitle);
                proposalDetailsLink.add(proposalLabel);                
            }
        };
        add(messages);		
	}
}
