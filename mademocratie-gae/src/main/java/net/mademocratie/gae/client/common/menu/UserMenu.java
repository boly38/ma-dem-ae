package net.mademocratie.gae.client.common.menu;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import net.mademocratie.gae.client.SignOutPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
public class UserMenu extends Panel {

	public UserMenu(String id) {
		super(id);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();		
		add(new Label("user-label", user.getNickname()));
		// ExternalLink userLogout = new ExternalLink("user-logout", userService.createLogoutURL("/"));
        BookmarkablePageLink<SignOutPage> userLogout
                = new BookmarkablePageLink<SignOutPage>("user-logout", SignOutPage.class, new PageParameters());
		add(userLogout);
		ExternalLink userProfile = new ExternalLink("user-profile", "#");
		add(userProfile);
		
	}

}
