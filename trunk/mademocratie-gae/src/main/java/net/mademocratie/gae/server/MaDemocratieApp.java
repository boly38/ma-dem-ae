package net.mademocratie.gae.server;

import net.mademocratie.gae.client.*;
import net.mademocratie.gae.client.common.AuthenticatedWebPage;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;

public class MaDemocratieApp extends WebApplication {
    // private static final Logger logger = Logger.getLogger(MaDemocratieApp.class.getName());	
	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

    /**
     * @see org.apache.wicket.protocol.http.WebApplication#newSession(Request, Response)
     */
    @Override
    public Session newSession(Request request, Response response)
    {
        return new CitizenSession(request);
    }

	@Override
	protected void init() {
		super.init();
        initSecurity();
		getResourceSettings().setResourcePollFrequency(null);
        GuiceModule guiceModule = new GuiceModule();
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, guiceModule));
        mountBookmarks();
	}

    private void initSecurity() {
        // Register the authorization strategy
        getSecuritySettings().setAuthorizationStrategy(new IAuthorizationStrategy()
        {
            public boolean isActionAuthorized(Component component, Action action)
            {
                // authorize everything
                return true;
            }

            public <T extends IRequestableComponent> boolean isInstantiationAuthorized(
                    Class<T> componentClass)
            {
                // Check if the new Page requires authentication (implements the marker interface)
                if (AuthenticatedWebPage.class.isAssignableFrom(componentClass))
                {
                    // Is user signed in?
                    if (((CitizenSession)Session.get()).isSignedIn())
                    {
                        // okay to proceed
                        return true;
                    }

                    // Intercept the request, but remember the target for later.
                    // Invoke Component.continueToOriginalDestination() after successful logon to
                    // continue with the target remembered.

                    throw new RestartResponseAtInterceptPageException(SignInPage.class);
                }

                // okay to proceed
                return true;
            }
        });
    }

    protected void mountBookmarks() {
        mountPage("about", AboutPage.class);
        mountPage("signin", SignInPage.class);
        mountPage("signout", SignOutPage.class);
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