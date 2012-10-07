package net.mademocratie.gae.client.common.menu;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class LinkMenu extends Panel {

	public LinkMenu(String id, String signInLabel) {
		super(id);
        UserService userService = UserServiceFactory.getUserService();		
		WebMarkupContainer liLink = new WebMarkupContainer("li-link");
		liLink.add(new ExternalLink("a-link", userService.createLoginURL("/"), signInLabel));
		add(liLink);
	}


}
