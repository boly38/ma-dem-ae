package net.mademocratie.gae.client.common.menu;

import net.mademocratie.gae.client.SignInPage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
public class LinkMenu extends Panel {

	public LinkMenu(String id, String signInLabel) {
		super(id);
        // UserService userService = UserServiceFactory.getUserService();
		WebMarkupContainer liLink = new WebMarkupContainer("li-link");
		// liLink.add(new ExternalLink("a-link", userService.createLoginURL("/"), signInLabel));
        BookmarkablePageLink<SignInPage> signInPageLink
                = new BookmarkablePageLink<SignInPage>("a-link", SignInPage.class, new PageParameters());
        liLink.add(signInPageLink);
		add(liLink);
	}


}
