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
package com.ammob.passport.webapp.util;

import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.ticket.TicketCreationException;
import org.springframework.web.util.CookieGenerator;

import com.ammob.passport.authentication.principal.InternalRememberMeUsernamePasswordCredentials;
import com.ammob.passport.model.User;

/**
 * Simple SecurityContext that stores the currently signed-in connection in a thread local.
 * @author Keith Donald
 */
public final class SecurityContext {

	private static final ThreadLocal<User> currentUser = new ThreadLocal<User>();

	public static User getCurrentUser() {
		User user = currentUser.get();
		if (user == null) {
			throw new IllegalStateException("No user is currently signed in");
		}
		return user;
	}

	public static void setCurrentUser(User user) {
		currentUser.set(user);
	}

	public static boolean userSignedIn() {
		return currentUser.get() != null;
	}

	public static void remove() {
		currentUser.remove();
	}
	
    /** 
     * Invoke generate validate Tickets and add the TGT to cookie. 
     * @param loginName     the user login name. 
     * @param loginPassword the user login password. 
     * @param response      the HttpServletResponse object. 
     * @param internal
     * @param rememberMe      rememberMe. 
     */  
	public static void addCasSignin(CentralAuthenticationService centralAuthenticationService, CookieGenerator ticketGrantingTicketCookieGenerator, 
    		String loginName, String loginPassword, boolean internal,  boolean rememberMe, HttpServletResponse response) 
    				throws TicketCreationException {
			InternalRememberMeUsernamePasswordCredentials credentials = new InternalRememberMeUsernamePasswordCredentials();
            credentials.setUsername(loginName);
            credentials.setPassword(loginPassword);
            credentials.setInternal(internal);
            credentials.setRememberMe(rememberMe);
            addCasSignin(centralAuthenticationService, ticketGrantingTicketCookieGenerator, credentials, response);
    }
	
    /** 
     * Invoke generate validate Tickets and add the TGT to cookie. 
     * @param loginName     the user login name. 
     * @param loginPassword the user login password. 
     * @param response      the HttpServletResponse object. 
     */  
	public static void addCasSignin(CentralAuthenticationService centralAuthenticationService, CookieGenerator ticketGrantingTicketCookieGenerator, 
			Credentials credentials, HttpServletResponse response) 
    				throws TicketCreationException {
        try {
            String ticketGrantingTicket = centralAuthenticationService.createTicketGrantingTicket(credentials);
            ticketGrantingTicketCookieGenerator.addCookie(response, ticketGrantingTicket);
        } catch (Exception e) {
        	throw new TicketCreationException();
        }
    }
}
