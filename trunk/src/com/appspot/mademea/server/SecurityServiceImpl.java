package com.appspot.mademea.server;

import java.util.logging.Logger;

import com.appspot.mademea.client.SecurityService;
import com.appspot.mademea.shared.AuthInfo;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SecurityServiceImpl extends RemoteServiceServlet implements SecurityService {
    private final static Logger LOGGER = Logger.getLogger(SecurityServiceImpl.class.getName()); 
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 6874559568610131326L;

	public AuthInfo getAuthInfo(String destinationUrl)
			throws IllegalArgumentException {
		AuthInfo authInfo = new AuthInfo();
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();
	    authInfo.setAuthenticated(user != null);
	    if (user != null) {
	    	authInfo.setNickname(user.getNickname());
	    	authInfo.setToggleAuthentUrl(userService.createLogoutURL(destinationUrl));
	    	authInfo.setEmail(user.getEmail());
	    	LOGGER.info("logged nick=" + user.getNickname() + " mail=" + user.getEmail());
	    } else {
	    	authInfo.setNickname(null);
	    	authInfo.setToggleAuthentUrl(userService.createLoginURL(destinationUrl));
	    	authInfo.setEmail(null);
	    	LOGGER.info("anonymous");
	    }
	    // LOGGER.info("toggleAuthentUrl=" + authInfo.getToggleAuthentUrl());
	    return authInfo;
	}

}
