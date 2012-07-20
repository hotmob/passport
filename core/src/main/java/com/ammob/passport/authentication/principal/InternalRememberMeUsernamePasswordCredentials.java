package com.ammob.passport.authentication.principal;

import org.jasig.cas.authentication.principal.RememberMeUsernamePasswordCredentials;

public class InternalRememberMeUsernamePasswordCredentials extends RememberMeUsernamePasswordCredentials {

	private static final long serialVersionUID = 1L;
	
	private boolean internal = false;
	
	public InternalRememberMeUsernamePasswordCredentials() {
		super();
	}
	
	public InternalRememberMeUsernamePasswordCredentials(String username, String password) {
		this();
		setUsername(username);
		setPassword(password);
	}
	
	public InternalRememberMeUsernamePasswordCredentials(String username, String password, boolean rememberMe) {
		this();
		setUsername(username);
		setPassword(password);
		setRememberMe(rememberMe);
	}
	
	public boolean isInternal() {
		return internal;
	}
	
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
}
