package net.mademocratie.gae.client.common.menu;

import net.mademocratie.gae.client.SignOutPage;
import net.mademocratie.gae.model.Citizen;
import net.mademocratie.gae.server.CitizenSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
public class UserMenu extends Panel {

	public UserMenu(String id) {
		super(id);
        Citizen currentUser = CitizenSession.get().getCitizen();
		add(new Label("user-label", currentUser.getPseudo()));
		// ExternalLink userLogout = new ExternalLink("user-logout", userService.createLogoutURL("/"));
        BookmarkablePageLink<SignOutPage> userLogout
                = new BookmarkablePageLink<SignOutPage>("user-logout", SignOutPage.class, new PageParameters());
		add(userLogout);
		ExternalLink userProfile = new ExternalLink("user-profile", "#");
		add(userProfile);
		
	}

}
