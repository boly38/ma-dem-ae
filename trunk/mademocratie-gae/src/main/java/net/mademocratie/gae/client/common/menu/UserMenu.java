package net.mademocratie.gae.client.common.menu;

import net.mademocratie.gae.client.HomePage;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.logging.Logger;

@SuppressWarnings("serial")
public class UserMenu extends Panel {
    private final static Logger LOGGER = Logger.getLogger(UserMenu.class.getName());

	public UserMenu(String id) {
		super(id);
        initComponents();
	}

    private void initComponents() {
        Citizen currentUser = CitizenSession.get().getCitizen();

        add(new Label("user-label", currentUser.getPseudo()));
        /*
		// ExternalLink userLogout = new ExternalLink("user-logout", userService.createLogoutURL("/"));
        BookmarkablePageLink<SignOutPage> userLogout
                = new BookmarkablePageLink<SignOutPage>("user-logout", SignOutPage.class, new PageParameters());
		add(userLogout);
        */
        LogoutLink logout = new LogoutLink("user-logout");
        add(logout);

        ExternalLink userProfile = new ExternalLink("user-profile", "#");
        add(userProfile);
    }


    /**
     * define a link for logout with confirmation
     */
    private class LogoutLink extends Link<Void> {

        private static final long serialVersionUID = -1082992144163134177L;

        public LogoutLink(String id) {
            super(id);
        }

        @Override
        public void onClick() {
            logout();
        }
    }

    public void logout() {
        if (CitizenSession.get() != null) {
            Citizen citizen = CitizenSession.get().getCitizen();
            if (citizen  != null) {
                LOGGER.info(citizen +" logged OUT with wicket session "+CitizenSession.get().getId());
            }
        }
        getSession().invalidate();
        setResponsePage(HomePage.class);
    }
}
