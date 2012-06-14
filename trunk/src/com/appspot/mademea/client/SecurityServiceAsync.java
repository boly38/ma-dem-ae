package com.appspot.mademea.client;

import com.appspot.mademea.shared.AuthInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SecurityServiceAsync {
	void getAuthInfo(String destinationUrl, AsyncCallback<AuthInfo> callback);
}
