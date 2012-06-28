package com.appspot.mademocratie.client;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

import com.appspot.mademocratie.client.common.FooterPanel;
import com.appspot.mademocratie.client.common.HeaderPanel;
import com.appspot.mademocratie.model.Proposal;
import com.appspot.mademocratie.server.service.IRepository;
import com.google.inject.Inject;

public class ProposalPage extends WebPage {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    // private final static Logger LOGGER = Logger.getLogger(ProposalPage.class.getName()); 
	//~design
    ProposalPage page;
    
    @Inject
    private IRepository<Proposal> proposalRepo;
    
    public ProposalPage(PageParameters params) {
        super(params);
        this.page = this;
//        initServices();
        initComponents();
    }

    private void initComponents() {
    	createCommons();
        createBreadCrumbs();
        createProposalDescription();
    }  
    
    private void createProposalDescription() {
    	PageParameters params = this.getPageParameters();
    	StringValue propIdStr = (params != null ? params.get("id") : null);
    	Long propId = null;
    	try {
    		propId = (propIdStr != null ? propIdStr.toLong() : null);
    	} catch (StringValueConversionException nfe) {
    		// nothing
    	}
    	Proposal p = (propId != null ? proposalRepo.get(propId) : null);
    	if (p == null) {
    		getSession().error("Unable to retrieve the proposal");
        	throw new RestartResponseException(HomePage.class, null);    		
    	}
    	String author = p.getAuthor() != null ? p.getAuthor().getNickname() : "An anonymous person ";
    	String pDescString = p.getContent();

        WebMarkupContainer pDescContainer = new WebMarkupContainer("pdesc-container");
        add(pDescContainer);
    	
    	Label proposalDate = new Label("proposalDate", p.getDate().toString());
    	Label proposalAuthor = new Label("proposalAuthor", author);
    	Label proposalTitle = new Label("proposalTitle", p.getTitle());
    	Label proposalDesc = new Label("proposalDescription", pDescString);
    	
    	add(proposalDate);
    	add(proposalAuthor);
    	add(proposalTitle);
    	pDescContainer.add(proposalDesc);
    	
    	pDescContainer.setVisible(pDescString != null);
	}

	private void createCommons() {
    	HeaderPanel headerPanel = new HeaderPanel("headerPanel", this);
    	FooterPanel footerPanel = new FooterPanel("footerPanel", this);
        add(headerPanel);
        add(footerPanel);
    }
    
    private void createBreadCrumbs() {
        // TODO breadcrumbs
    }    
}