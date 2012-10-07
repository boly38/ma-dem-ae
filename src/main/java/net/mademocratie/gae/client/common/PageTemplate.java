package net.mademocratie.gae.client.common;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import net.mademocratie.gae.server.MaDemocratieApp;

public abstract class PageTemplate extends WebPage {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5140576510124285604L;
	/*
     * page parameters
     */
    protected PageParameters params;    
    /**
     * application
     */
    @SuppressWarnings("unused")
	private transient MaDemocratieApp app;
    /**
     * PageTemplate constructor
     * @param params - page parameters map
     */
    public PageTemplate(final PageParameters params) {
    	super(params);
        this.params = params;
        app = (MaDemocratieApp)getApplication();
        createCommons();
        setPagetitle();
    }
    
    private void createCommons() {
    	HeaderPanel headerPanel = new HeaderPanel("headerPanel", this);
    	FooterPanel footerPanel = new FooterPanel("footerPanel", this);
        add(headerPanel);
        add(footerPanel);
    }

    protected void setPagetitle() {
        /* set head page title to display in browser title bar */
        add(new Label("head_page_title", "MaDémocratie"));
    }

    public String getRequestUrl(){
    	// src: https://cwiki.apache.org/WICKET/getting-a-url-for-display.html
    	return RequestCycle.get().getUrlRenderer().renderFullUrl(
    			   Url.parse(urlFor(getClass(),null).toString()));

    }    
}
