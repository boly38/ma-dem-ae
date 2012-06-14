package com.appspot.mademea.client;

import com.appspot.mademea.shared.AuthInfo;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("security")
public interface SecurityService extends RemoteService {
	AuthInfo getAuthInfo(String destinationUrl) throws IllegalArgumentException;
}
