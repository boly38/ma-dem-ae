package com.appspot.mademea.shared;

import java.io.Serializable;

public class AuthInfo implements Serializable {
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 3281505383982458542L;

	private boolean isAuthenticated = false;
	private String email;
	private String nickname;
	private String toggleAuthentUrl;
	public boolean isAuthenticated() {
		return isAuthenticated;
	}
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getToggleAuthentUrl() {
		return toggleAuthentUrl;
	}
	public void setToggleAuthentUrl(String toggleAuthentUrl) {
		this.toggleAuthentUrl = toggleAuthentUrl;
	}
	
}
