/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ammob.passport.webapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ammob.passport.model.User;
import com.ammob.passport.webapp.util.SecurityContext;
import com.ammob.passport.webapp.util.UserCookieGenerator;

/**
 * Before a request is handled:
 * 1. sets the current User in the {@link SecurityContext} from a cookie, if present and the user is still connected to Facebook.
 * 2. requires that the user sign-in if he or she hasn't already.
 * @author Keith Donald
 */
public final class UserInterceptor extends HandlerInterceptorAdapter {

	private final UsersConnectionRepository connectionRepository;
	
	private final UserCookieGenerator userCookieGenerator = new UserCookieGenerator();

	public UserInterceptor(UsersConnectionRepository connectionRepository) {
		this.connectionRepository = connectionRepository;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		rememberUser(request, response);
//		handleSignOut(request, response);			
//		if (SecurityContext.userSignedIn() || requestForSignIn(request)) {System.out.println("1111111111111");
			return true;
//		} else {
//			return requireSignIn(request, response);
//		}
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//		SecurityContext.remove();
	}

	// internal helpers
	@SuppressWarnings("unused")
	private void rememberUser(HttpServletRequest request, HttpServletResponse response) {
		String userId = userCookieGenerator.readCookieValue(request);
		if (userId == null) {
			return;
		}
		if (!userNotFound(userId)) {
			userCookieGenerator.removeCookie(response);
			return;
		}
		SecurityContext.setCurrentUser(new User(userId));
	}
	
	@SuppressWarnings("unused")
	private void handleSignOut(HttpServletRequest request, HttpServletResponse response) {
		if (SecurityContext.userSignedIn() && request.getServletPath().startsWith("/signout")) {
			connectionRepository.createConnectionRepository(SecurityContext.getCurrentUser().getId().toString()).removeConnections("facebook");
			userCookieGenerator.removeCookie(response);
			SecurityContext.remove();			
		}
	}
	
	@SuppressWarnings("unused")
	private boolean requestForSignIn(HttpServletRequest request) {
		return request.getServletPath().contains("/connect");
	}
	
	@SuppressWarnings("unused")
	private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return true;
//		new RedirectView("/connect", true).render(null, request, response);
//		return false;
	}

	private boolean userNotFound(String userId) {
		// doesn't bother checking a local user database: simply checks if the userId is connected to Facebook
		return connectionRepository.createConnectionRepository(userId).findPrimaryConnection(Facebook.class) != null;
	}
	
}