package net.mademocratie.gae.client.common;

import net.mademocratie.gae.server.CitizenSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import net.mademocratie.gae.server.MaDemocratieApp;

import java.util.logging.Logger;

public abstract class PageTemplate extends WebPage {
    protected static Logger LOGGER = Logger.getLogger(PageTemplate.class.getName());
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
	private transient MaDemocratieApp maDemocratieApp;

    protected PageTemplate() {
        super();
    }

    /**
     * PageTemplate constructor
     * @param params - page parameters map
     */
    public PageTemplate(PageParameters params) {
    	super(params);
        this.params = params;
        maDemocratieApp = (MaDemocratieApp)getApplication();
        createCommons();
        setPageTitle();
    }
    
    private void createCommons() {
    	HeaderPanel headerPanel = new HeaderPanel("headerPanel", this);
    	FooterPanel footerPanel = new FooterPanel("footerPanel", this);
        add(headerPanel);
        add(footerPanel);
    }

    protected void setPageTitle() {
        /* set head page title to display in browser title bar */
        add(new Label("head_page_title", "MaDemocratie"));
    }

    public String getRequestUrl(){
    	// src: https://cwiki.apache.org/WICKET/getting-a-url-for-display.html
    	return RequestCycle.get().getUrlRenderer().renderFullUrl(
    			   Url.parse(urlFor(getClass(),null).toString()));

    }

    public void addFeedbackSuccess(String msg) {
        LOGGER.info("addFeedbackSuccess:"+msg);
        CitizenSession.get().success(msg);
    }

    protected void setResponseNoCache() {
        final WebResponse response = (WebResponse)getResponse();
        response.setHeader("Cache-Control", "no-cache, max-age=0, must-revalidate"); // no-store
        response.setHeader("Cache-Control", "no-store");
        // response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
    }

    public MaDemocratieApp getMaDemocratieApp() {
        return maDemocratieApp;
    }
}
