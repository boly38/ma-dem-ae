package com.appspot.mademocratie.client;

import java.util.List;
import java.util.logging.Logger;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.IProposal;
import com.appspot.mademocratie.server.service.IRepository;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.inject.Inject;

public class HomePage extends WebPage {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    private final static Logger LOGGER = Logger.getLogger(HomePage.class.getName()); 
	//~design
	HomePage page;
    private WebMarkupContainer container;	
    private ProposalCreatePanel propCreatePanel;
    
    // ~services
    @Inject
    private IProposal proposalsQueries;

    public HomePage(PageParameters params) {
        super(params);
//        initServices();
        initComponents();
        this.page = this;
    }
	
//    private void initServices() {
//    	MaDemocratieApp app = (MaDemocratieApp)getApplication();
//    	securityService = app.getInjector().getInstance(SecurityService.class);    	
//    }
	
    private void initComponents() {
    	createHelloUser();
        createPropFormPanel();
        createProposalsList();
//        setPagetitle();
//        createFirstLevelNavigation();
//        createBreadCrumbs();
//        createGlobalFeedbackPanel();
//        createNewAppButton();
//        createAppsTable();

    }

    private void createHelloUser() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        WebMarkupContainer helloUser = new WebMarkupContainer("hello-user");
        add(helloUser);

        WebMarkupContainer helloAnon = new WebMarkupContainer("hello-anon");
        add(helloAnon);

        Label userNickname = new Label("user");
        helloUser.add(userNickname);

        ExternalLink signOut = new ExternalLink("sign-out", userService.createLogoutURL("/" + getRequest().getClientUrl()));
        helloUser.add(signOut);

        ExternalLink signIn = new ExternalLink("sign-in", userService.createLoginURL("/" + getRequest().getClientUrl()));
        helloAnon.add(signIn);

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

	private void createPropFormPanel() {
        container = new WebMarkupContainer("createPropContainer");
        container.setOutputMarkupId(true);
        propCreatePanel = new ProposalCreatePanel("propCreateForm", page);
        container.add(propCreatePanel);
        add(container);
    }   
	
	private void createProposalsList() {
        // This LoadableDetachableModel allows the following ListView<Proposal> to always load the latest persisted
        // Proposal entities on-demand, without having to store any model data in the session when this Guestbook page
        // gets serialized.
        LoadableDetachableModel<List<Proposal>> latestProposals = new LoadableDetachableModel<List<Proposal>>()
        {
            @Override
            protected List<Proposal> load()
            {
            	LOGGER.info("load proposals");
                return proposalsQueries.latest(5);
            }
        };

//        if (!latestProposals.getObject().isEmpty())
//        {
//            noMessages.setVisible(false);
//        }

        ListView<Proposal> messages = new ListView<Proposal>("proposals", latestProposals)
        {
            @Override
            protected void populateItem(ListItem<Proposal> item)
            {
                Proposal Proposal = item.getModel().getObject();
                String email = Proposal.getAuthor() != null ? Proposal.getAuthor().getNickname() : "An anonymous person ";
                item.add(new Label("author", email));
                item.add(new Label("proposal", Proposal.getTitle()));
            }
        };
        add(messages);		
	}
}
