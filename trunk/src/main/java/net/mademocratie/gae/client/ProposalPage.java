package net.mademocratie.gae.client;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.string.StringValueConversionException;

import net.mademocratie.gae.client.common.PageTemplate;
import net.mademocratie.gae.model.Proposal;
import net.mademocratie.gae.server.service.IRepository;
import com.google.inject.Inject;
import java.util.logging.Logger;

public class ProposalPage extends PageTemplate {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    private final static Logger LOGGER = Logger.getLogger(ProposalPage.class.getName()); 
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
    	LOGGER.info("display proposal n�" + propId);
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
    
    private void createBreadCrumbs() {
        // TODO breadcrumbs
    }   
}