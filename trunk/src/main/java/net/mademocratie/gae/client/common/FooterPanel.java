package net.mademocratie.gae.client.common;

import org.apache.wicket.markup.html.panel.Panel;

public class FooterPanel extends Panel {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8262836430220919697L;
    // private final static Logger LOGGER = Logger.getLogger(FooterPanel.class.getName());

	private PageTemplate parentPage;
    
    public FooterPanel(String id, PageTemplate parentPage) {
        super(id);
        this.setParentPage(parentPage);
        initComponents();
    }
    
    private void initComponents() {
    	String reqUrl = parentPage.getRequestUrl();
    	AnalyticsPanel anaPanel = new AnalyticsPanel("analytics-panel");
    	if (reqUrl.contains("localhost")) {
    		anaPanel.setVisible(false);
    		anaPanel.setVisibilityAllowed(false);
    		// LOGGER.info("no analytics for local url " + reqUrl);
    	}
    	add(anaPanel);
    }

	public PageTemplate getParentPage() {
		return parentPage;
	}

	public void setParentPage(PageTemplate parentPage) {
		this.parentPage = parentPage;
	}    
}
