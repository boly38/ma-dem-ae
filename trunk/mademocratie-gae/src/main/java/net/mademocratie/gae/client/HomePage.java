package net.mademocratie.gae.client;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;
import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IManageCitizen;
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

import java.util.ArrayList;
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

    // ~services
    @Inject
    private IManageProposal manageProposals;

    @Inject
    private IManageCitizen manageCitizens;

    /**
     * Constructor
     */
    public HomePage() {
        super(new PageParameters());
        initComponents();
        this.page = this;
    }

    @SuppressWarnings("unused") // wicket use it
    public HomePage(PageParameters params) {
        super(params);
        initComponents();
        this.page = this;
    }

    private void initComponents() {
	    createFeedback();
    	createHelloUser();
        createProposalsList();
        createCitizensList();
    }

    private void createFeedback() {
        FeedbackPanel feedback = new FeedbackPanel("feedback");
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
                try {
                    return manageProposals.latest(5);
                } catch (Exception e) {
                    String errMsg = "Unable to load proposals : " + e.getMessage();
                    LOGGER.severe(errMsg);
                    error(errMsg);
                    return new ArrayList<Proposal>();
                }
            }
        };

//        if (!latestProposals.getObject().isEmpty())
//        {
//            noMessages.setVisible(false);
//        }

        ListView<Proposal> proposals = new ListView<Proposal>("proposals", latestProposals) {
            /**
			 * serialVersionUID
			 */
			private static final long serialVersionUID = 2961302430978521634L;

			@Override
            protected void populateItem(ListItem<Proposal> item) {
                populateProposal(item);
            }
        };
        add(proposals);
	}


    private void createCitizensList() {
        // BookmarkablePageLink<CitizensPage> citizensLink
        //         = new BookmarkablePageLink<CitizensPage>("last-citizens", CitizensPage.class, new PageParameters());
        // add(citizensLink);
        LoadableDetachableModel<List<Citizen>> latestCitizen = new LoadableDetachableModel<List<Citizen>>() {
            @Override
            protected List<Citizen> load()
            {
                LOGGER.info("load citizen");
                try {
                    return manageCitizens.latest(5);
                } catch (Exception e) {
                    String errMsg = "Unable to load citizens: " + e.getMessage();
                    LOGGER.severe(errMsg);
                    error(errMsg);
                    return new ArrayList<Citizen>();
                }
            }
        };

//        if (!latestCitizen.getObject().isEmpty())
//        {
//            noMessages.setVisible(false);
//        }

        ListView<Citizen> citizens = new ListView<Citizen>("citizens", latestCitizen) {
            @Override
            protected void populateItem(ListItem<Citizen> item) {
                populateCitizen(item);
            }
        };
        add(citizens);
    }

    private void populateCitizen(ListItem<Citizen> item) {
        Citizen citizen = item.getModel().getObject();
        String pseudo = citizen.getPseudo();
        item.add(new Label("pseudo", pseudo));
        /*
        PageParameters params = new PageParameters();
        params.set("id", proposal.getId());
        BookmarkablePageLink<ProposalPage> proposalDetailsLink
                = new BookmarkablePageLink<ProposalPage>("proposal", ProposalPage.class, params);
        item.add(proposalDetailsLink);
        Label proposalLabel = new Label("proposalLabel", proposalTitle);
        proposalDetailsLink.add(proposalLabel);
        */
    }


    private void populateProposal(ListItem<Proposal> item) {
        Proposal proposal = item.getModel().getObject();
        String email = proposal.getAuthorPseudo() != null ? proposal.getAuthorPseudo() : "An anonymous person ";
        item.add(new Label("author", email));
        String proposalTitle = proposal.getTitle();
        if (proposalTitle != null && proposalTitle.length() > 30) {
            proposalTitle = proposalTitle.substring(0, 30).concat("...");
        }
        PageParameters params = new PageParameters();
        params.set("id", proposal.getId());
        BookmarkablePageLink<ProposalPage> proposalDetailsLink
                = new BookmarkablePageLink<ProposalPage>("proposal", ProposalPage.class, params);
        item.add(proposalDetailsLink);
        Label proposalLabel = new Label("proposalLabel", proposalTitle);
        proposalDetailsLink.add(proposalLabel);
    }
}
