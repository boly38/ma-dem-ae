package com.appspot.mademocratie.client.common.menu;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UserMenu extends Panel {

	public UserMenu(String id) {
		super(id);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();		
		add(new Label("user-label", user.getNickname()));
		ExternalLink userLogout = new ExternalLink("user-logout", userService.createLogoutURL("/"));
		add(userLogout);
		ExternalLink userProfile = new ExternalLink("user-profile", "#");
		add(userProfile);
		
	}

}
