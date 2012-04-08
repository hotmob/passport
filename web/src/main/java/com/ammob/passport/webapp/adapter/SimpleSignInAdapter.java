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
package com.ammob.passport.webapp.adapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.ticket.TicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.util.CookieGenerator;

/**
 * Signs the user in by setting the currentUser property on the {@link SecurityContext}.
 * Remembers the sign-in after the current request completes by storing the user's id in a cookie.
 * This is cookie is read in {@link UserInterceptor#preHandle(HttpServletRequest, HttpServletResponse, Object)} on subsequent requests.
 * @author Keith Donald
 * @see UserInterceptor
 */
public class SimpleSignInAdapter implements SignInAdapter {

	protected final transient Log log = LogFactory.getLog(getClass());
	
	private final RequestCache requestCache;

	private CentralAuthenticationService centralAuthenticationService;
	
	private CookieGenerator ticketGrantingTicketCookieGenerator;
	
	@Autowired
	public void setCentralAuthenticationService(CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}
	
	@Autowired
	public void setTicketGrantingTicketCookieGenerator(CookieGenerator ticketGrantingTicketCookieGenerator) {
		this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	}
	
	@Inject
	public SimpleSignInAdapter(RequestCache requestCache) {
		this.requestCache = requestCache;
	}
	
	public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {
		//SignInUtil.signin(localUserId);
		log.info("+++++++++++++++++++++++++++++++++0 : " + localUserId);
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(localUserId, null, null));	
		String dd = extractOriginalUrl(request);
		log.info("+++++++++++++++++++++++++++++++++ : " + dd);
		return dd;
	}

	private String extractOriginalUrl(NativeWebRequest request) {
		HttpServletRequest nativeReq = request.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeRes = request.getNativeResponse(HttpServletResponse.class);
		SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		if (saved == null) {
			return null;
		}
		requestCache.removeRequest(nativeReq, nativeRes);
		removeAutheticationAttributes(nativeReq.getSession(false));
		return saved.getRedirectUrl();
	}
		 
	private void removeAutheticationAttributes(HttpSession session) {
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
    /** 
     * Invoke generate validate Tickets and add the TGT to cookie. 
     * @param loginName     the user login name. 
     * @param loginPassword the user login password. 
     * @param response      the HttpServletResponse object. 
     */  
    protected void bindTicketGrantingTicket(String loginName, String loginPassword, HttpServletResponse response) {
        try {
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials();
            credentials.setUsername(loginName);
            credentials.setPassword(loginPassword);
            String ticketGrantingTicket = centralAuthenticationService.createTicketGrantingTicket(credentials);
            ticketGrantingTicketCookieGenerator.addCookie(response, ticketGrantingTicket);
        } catch (TicketException te) {
        	log.error("Validate the login name " + loginName + " failure, can't bind the TGT!", te);
        } catch (Exception e){
        	log.error("bindTicketGrantingTicket has exception.", e);
        }
    }
}