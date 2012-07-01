package com.appspot.mademocratie.client.common;

import org.apache.wicket.Page;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.appspot.mademocratie.client.ProposalsPage;

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
    
    private void addActiveStyle(WebMarkupContainer webContainer) { {
    	webContainer.add(new AttributeAppender("class", new Model<String>("active")));
    }
    	
    }
    
    private void initComponents() {
        WebMarkupContainer menuLiHome = new WebMarkupContainer("li-home");
        add(menuLiHome);
        WebMarkupContainer menuAHome = new WebMarkupContainer("a-home");
        menuLiHome.add(menuAHome);
        WebMarkupContainer menuLiLast = new WebMarkupContainer("li-last");
        add(menuLiLast);
        WebMarkupContainer menuALast = new WebMarkupContainer("a-last");
        menuLiLast.add(menuALast);
        WebMarkupContainer menuLiSearch = new WebMarkupContainer("li-search");
        add(menuLiSearch);
        WebMarkupContainer menuAsearch = new WebMarkupContainer("a-search");
        menuLiSearch.add(menuAsearch);        

        String currentPage = this.parentPage.getClass().getSimpleName(); 
        String appHomePage = getApplication().getHomePage().getSimpleName();
        if (appHomePage.equals(currentPage)) {
        	addActiveStyle(menuLiHome);
        } else if (currentPage.equals(ProposalsPage.class.getSimpleName())) {
        	addActiveStyle(menuLiLast);
        }
    }

	public Page getParentPage() {
		return parentPage;
	}

	public void setParentPage(Page parentPage) {
		this.parentPage = parentPage;
	}    
}
