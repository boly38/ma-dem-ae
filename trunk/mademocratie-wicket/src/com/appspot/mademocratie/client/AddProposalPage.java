package com.appspot.mademocratie.client;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.appspot.mademocratie.client.common.FooterPanel;
import com.appspot.mademocratie.client.common.HeaderPanel;

public class AddProposalPage extends WebPage {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -2388947538013186136L;

    // private final static Logger LOGGER = Logger.getLogger(ProposalPage.class.getName()); 
	//~design
	AddProposalPage page;

    private ProposalCreatePanel proposalCreatePanel;
    
    public AddProposalPage(PageParameters params) {
        super(params);
        this.page = this;
//        initServices();
        initComponents();
    }

    private void initComponents() {
    	createCommons();
    	createProposalPanel();
    }  

	private void createCommons() {
    	HeaderPanel headerPanel = new HeaderPanel("headerPanel", this);
    	FooterPanel footerPanel = new FooterPanel("footerPanel", this);
        add(headerPanel);
        add(footerPanel);
    }
    
	private void createProposalPanel() {
		proposalCreatePanel = new ProposalCreatePanel("proposalCreatePanel", page);
        add(proposalCreatePanel);
    }   
	
}
