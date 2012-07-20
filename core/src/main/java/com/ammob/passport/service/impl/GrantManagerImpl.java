package com.ammob.passport.service.impl;

import java.net.URI;
import java.util.Calendar;
import java.util.Locale;

import javax.jws.WebService;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.ticket.InvalidTicketException;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.util.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ammob.passport.authentication.principal.InternalRememberMeUsernamePasswordCredentials;
import com.ammob.passport.service.GrantService;

@Service("grantManager")
@WebService(serviceName = "GrantService", endpointInterface = "com.ammob.passport.service.GrantService")
public class GrantManagerImpl implements GrantService {
	
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    
    @Autowired
    private CentralAuthenticationService centralAuthenticationService;
    
    @Autowired
    @NotNull
    private HttpClient httpClient;
    
	@Override
	public Response getGrantTicket(String username, String password, boolean rememberMe, UriInfo uriInfo) {
        try {
        	if(StringUtils.hasText(username) && StringUtils.hasText(username) && uriInfo != null) {
	            final String tgt = this.centralAuthenticationService.createTicketGrantingTicket(new InternalRememberMeUsernamePasswordCredentials(username, password));
	            URI newItemURI = uriInfo.getRequestUriBuilder().path(tgt).build();
	            ResponseBuilder builder = Response.ok("<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\"><html><head><title>" + Response.Status.CREATED.getStatusCode() + " " + Response.Status.CREATED.getReasonPhrase() + "</title></head><body><h1>TGT Created</h1><form action=\"" + newItemURI.toString() + "\" method=\"POST\">Service:<input type=\"text\" name=\"service\" value=\"\"><br><input type=\"submit\" value=\"Submit\"></form></body></html>");  
	            builder.location(newItemURI);
	            builder.status(Response.Status.CREATED);
	            return builder.build();
        	}
        } catch (final TicketException e) {
            log.warn(e.getMessage() + ", username = " + username + ", password = " + password + ", rememberMe = " + rememberMe);
        }  catch (final Exception e) {
            log.warn(e.getMessage() + ", username = " + username + ", password = " + password + ", rememberMe = " + rememberMe);
        }
        return Response.status(Response.Status.NOT_FOUND).build();
	}

	@Override
	public Response getServerTicket(String tgt, String serviceUrl) {
		try {
			final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(tgt, new SimpleWebApplicationServiceImpl(serviceUrl, this.httpClient));
			return Response.ok(serviceTicketId, MediaType.TEXT_PLAIN).build();
		} catch (final InvalidTicketException e) {
			log.error(e.getMessage() + ", ServiceUrl = " + serviceUrl + ", tgt = " + tgt);
			return Response.status(Response.Status.BAD_REQUEST).build();
		} catch (final Exception e) {
			log.error(e.getMessage() + ", ServiceUrl = " + serviceUrl + ", tgt = " + tgt);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
	
	@Override
	public Response removeServerTicket(String tgt) {
		this.centralAuthenticationService.destroyTicketGrantingTicket(tgt);
		return Response.status(Response.Status.OK).build();
	}
	
	@Override
	public String getDateTime() {
		DateFormatter formatter = new DateFormatter("dd/MM/yyyy hh:mm:ss");
		return formatter.print(Calendar.getInstance().getTime(), Locale.getDefault());
	}
}
