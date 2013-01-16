package net.mademocratie.gae.server;

import net.mademocratie.gae.client.AboutPage;
import net.mademocratie.gae.client.ActivatePage;
import net.mademocratie.gae.client.ExceptionPage;
import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.client.citizen.RegisterPage;
import net.mademocratie.gae.client.citizen.SignInPage;
import net.mademocratie.gae.client.common.AuthenticatedWebPage;
import net.mademocratie.gae.client.proposal.AddProposalPage;
import net.mademocratie.gae.client.proposal.ProposalPage;
import net.mademocratie.gae.client.proposal.ProposalsPage;
import org.apache.wicket.*;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.settings.IApplicationSettings;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MaDemocratieApp extends WebApplication {
    private final static Logger LOGGER = Logger.getLogger(MaDemocratieApp.class.getName());

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

    /**
     * @see org.apache.wicket.protocol.http.WebApplication#newSession(Request, Response)
     */
    @Override
    public Session newSession(Request request, Response response) {
        CitizenSession session = new CitizenSession(request);
        // english is default language
        session.setLocale(Locale.US);
        return session;
    }

	@Override
	protected void init() {
        LOGGER.info("Wicket application init configurationType:" + getConfigurationType());
        initSettings();
        initSecurity();
		getResourceSettings().setResourcePollFrequency(null);
        MaDemocratieGuiceModule maDemocratieGuiceModule = new MaDemocratieGuiceModule();
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, maDemocratieGuiceModule));
        mountBookmarks();
        super.init();
    }

    private void initSettings() {
        // com.google.inject.internal.util.$FinalizableReferenceQueue <init>: Failed to start reference finalizer thread. Reference cleanup will only occur when new references are created.
        // java.lang.reflect.InvocationTargetException
        // see also http://code.google.com/p/google-guice/issues/detail?id=488
        Logger.getLogger("com.google.inject.internal.util").setLevel(Level.WARNING);

        if (!RuntimeConfigurationType.DEVELOPMENT.equals(getConfigurationType())) {
            getMarkupSettings().setStripWicketTags(true);
        }
        IApplicationSettings settings = getApplicationSettings();
        settings.setInternalErrorPage(ExceptionPage.class);
        // getRequestCycleListeners().add(new ExecutionHandlerRequestCycle(this));
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
        mountPage("register", RegisterPage.class);
        mountPage("activate", ActivatePage.class);
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