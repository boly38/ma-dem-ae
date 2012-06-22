package com.appspot.mademocratie.server;

import java.util.logging.Logger;

import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;

import com.appspot.mademocratie.client.HomePage;

public class MaDemocratieApp extends WebApplication {
    private static final Logger logger = Logger.getLogger(MaDemocratieApp.class.getName());	
	@Override
	public Class getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		getResourceSettings().setResourcePollFrequency(null);
        GuiceModule guiceModule = new GuiceModule();
        getComponentInstantiationListeners().add(new GuiceComponentInjector(this, guiceModule));		
	}

	protected ISessionStore newSessionStore() {
		return new HttpSessionStore();
	}
}