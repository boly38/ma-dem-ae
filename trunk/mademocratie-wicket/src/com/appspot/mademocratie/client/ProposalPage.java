package com.appspot.mademocratie.client;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.appspot.mademocratie.client.common.FooterPanel;
import com.appspot.mademocratie.client.common.HeaderPanel;

public class ProposalPage extends WebPage {
    /**
	 * serialUID
	 */
	private static final long serialVersionUID = -8997275197440920875L;

    // private final static Logger LOGGER = Logger.getLogger(ProposalPage.class.getName()); 
	//~design
    ProposalPage page;
    

    public ProposalPage(PageParameters params) {
        super(params);
//        initServices();
        initComponents();
        this.page = this;
    }

    private void initComponents() {
    	createCommons();
        createBreadCrumbs();
    }  
    
    private void createCommons() {
    	HeaderPanel headerPanel = new HeaderPanel("headerPanel", page);
    	FooterPanel footerPanel = new FooterPanel("footerPanel", page);
        add(headerPanel);
        add(footerPanel);
    }
    
    private void createBreadCrumbs() {
        // TODO breadcrumbs
    }    
}