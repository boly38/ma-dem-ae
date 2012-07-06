package com.appspot.mademocratie.client;

import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.appspot.mademocratie.client.common.PageTemplate;
import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.IManageProposal;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

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
        initComponents();
        this.page = this;
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

    private String getRequestUrl(){
    	// src: https://cwiki.apache.org/WICKET/getting-a-url-for-display.html
    	return RequestCycle.get().getUrlRenderer().renderFullUrl(
    			   Url.parse(urlFor(HomePage.class,null).toString()));

    }
    

    private void createHelloUser() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();


        String boLink = "https://appengine.google.com/dashboard?&app_id=s~ma-dem-ae";
        String loginUrl = userService.createLoginURL("/" + getRequest().getClientUrl());
        String logoutUrl = userService.createLogoutURL("/" + getRequest().getClientUrl());
        if (getRequestUrl().contains("localhost")) {
        	boLink = "/_ah/admin";
        }
        
        WebMarkupContainer helloUser = new WebMarkupContainer("hello-user");
        add(helloUser);

        WebMarkupContainer helloAnon = new WebMarkupContainer("hello-anon");
        add(helloAnon);
        
        WebMarkupContainer specialAdmin = new WebMarkupContainer("special-admin");
        add(specialAdmin);

        Label userNickname = new Label("user");
        helloUser.add(userNickname);

        ExternalLink signOut = new ExternalLink("sign-out", logoutUrl);
        helloUser.add(signOut);

        ExternalLink signIn = new ExternalLink("sign-in", loginUrl);
        helloAnon.add(signIn);
        
    	ExternalLink linkBackOffice = new ExternalLink("link-backoffice", boLink);
    	specialAdmin.add(linkBackOffice);

       	specialAdmin.setVisible((userService.isUserLoggedIn() && userService.isUserAdmin()));

        if (user != null)
        {
            userNickname.setDefaultModel(new Model<String>(user.getNickname()));
            helloAnon.setVisible(false);
        }
        else
        {
            helloUser.setVisible(false);
        }    	
    }

	private void createProposalsList() {
		BookmarkablePageLink<ProposalsPage> proposalsLink = new BookmarkablePageLink<ProposalsPage>("last-proposals", ProposalsPage.class, null);
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
                BookmarkablePageLink<ProposalPage> proposalDetailsLink = new BookmarkablePageLink<ProposalPage>("proposal", ProposalPage.class, params);
                item.add(proposalDetailsLink);
                Label proposalLabel = new Label("proposalLabel", proposalTitle);
                proposalDetailsLink.add(proposalLabel);                
            }
        };
        add(messages);		
	}
}
