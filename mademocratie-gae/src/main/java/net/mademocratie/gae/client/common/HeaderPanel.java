package net.mademocratie.gae.client.common;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import net.mademocratie.gae.client.ProposalsPage;
import net.mademocratie.gae.client.common.menu.LinkMenu;
import net.mademocratie.gae.client.common.menu.UserMenu;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class HeaderPanel extends Panel {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8262836430220919697L;
	private PageTemplate parentPage;
    
    public HeaderPanel (String id, PageTemplate parentPage) {
        super(id);
        this.setParentPage(parentPage);    
        initComponents();
        initUserMenu();
    }
    
    private void addActiveStyle(WebMarkupContainer webContainer) {
    	webContainer.add(new AttributeAppender("class", new Model<String>("active")));    	
    }
    
    private void initUserMenu() {
        UserService userService = UserServiceFactory.getUserService();
        // User user = userService.getCurrentUser();
        if (userService.isUserLoggedIn()) {
        	add(new UserMenu("user-menu"));
        } else {
        	add(new LinkMenu("user-menu", "sign-in !"));
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

	public PageTemplate getParentPage() {
		return parentPage;
	}

	public void setParentPage(PageTemplate parentPage) {
		this.parentPage = parentPage;
	}    
}
