package com.appspot.mademocratie.server;

import org.apache.wicket.Page;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;

import com.appspot.mademocratie.client.AddProposalPage;
import com.appspot.mademocratie.client.HomePage;
import com.appspot.mademocratie.client.ProposalPage;

public class MaDemocratieApp extends WebApplication {
    // private static final Logger logger = Logger.getLogger(MaDemocratieApp.class.getName());	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		getResourceSettings().setResourcePollFrequency(null);
        GuiceModule guiceModule = new GuiceModule();
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, guiceModule));
        mountBookmarks();
	}

    protected void mountBookmarks() {
    	mountPage("proposal/${id}", ProposalPage.class);
    	// TODO 
    	// MixedParamUrlCodingStrategy proposalUrl = new MixedParamUrlCodingStrategy("proposal",ProposalPage.class, new String[]{"id"}
    	// mountPage(proposalUrl);
    	mountPage("addproposal", AddProposalPage.class);
    	// TOFIX annotation seems insufisant
        // AnnotatedMountScanner annotatedMountScanner = new AnnotatedMountScanner();
        // annotatedMountScanner.scanPackage("com.appspot.mademocratie.client").mount(this);
    }
	
	protected ISessionStore newSessionStore() {
		return new HttpSessionStore();
	}
}