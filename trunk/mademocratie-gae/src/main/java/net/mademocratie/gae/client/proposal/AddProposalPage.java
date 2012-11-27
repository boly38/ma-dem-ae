package net.mademocratie.gae.client.proposal;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import net.mademocratie.gae.client.common.PageTemplate;

public class AddProposalPage extends PageTemplate {
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
    	createProposalPanel();
    }  
    
	private void createProposalPanel() {
		proposalCreatePanel = new ProposalCreatePanel("proposalCreatePanel", page);
        add(proposalCreatePanel);
    }   
	
}
