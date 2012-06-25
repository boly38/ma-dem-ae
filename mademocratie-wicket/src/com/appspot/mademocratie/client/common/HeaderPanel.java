package com.appspot.mademocratie.client.common;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.Panel;

public class HeaderPanel extends Panel {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8262836430220919697L;
	private Page parentPage;
    
    public HeaderPanel (String id, Page parentPage) {
        super(id);
        this.setParentPage(parentPage);
        initComponents();
    }
    
    private void initComponents() {
    	// Nothing to do
    }

	public Page getParentPage() {
		return parentPage;
	}

	public void setParentPage(Page parentPage) {
		this.parentPage = parentPage;
	}    
}
