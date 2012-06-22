package com.appspot.mademocratie.server.service;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SecurityServiceImpl implements ISecurityService {

	@Override
	public String getCurrentUserNick() {
		UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return (user != null ? user.getNickname() : "anonymous");
	}
}
