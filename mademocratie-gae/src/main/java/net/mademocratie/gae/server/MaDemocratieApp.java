package net.mademocratie.gae.server;

import org.apache.wicket.Page;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;

import net.mademocratie.gae.client.AboutPage;
import net.mademocratie.gae.client.AddProposalPage;
import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.client.ProposalPage;
import net.mademocratie.gae.client.ProposalsPage;

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
    	mountPage("about", AboutPage.class);
    	mountPage("addproposal", AddProposalPage.class);
    	mountPage("proposals", ProposalsPage.class);
    	mountPage("proposal/${id}", ProposalPage.class);
    	// TO FIX annotation seems insufisant
        // AnnotatedMountScanner annotatedMountScanner = new AnnotatedMountScanner();
        // annotatedMountScanner.scanPackage("net.mademocratie.gae.client").mount(this);
    }
	
	protected ISessionStore newSessionStore() {
		return new HttpSessionStore();
	}
}