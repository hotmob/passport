package com.ammob.passport.webapp.flow;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.services.UnauthorizedServiceException;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.validation.constraints.NotNull;

/**
 * Performs a basic check if an authentication request for a provided service is authorized to proceed
 * based on the registered services registry configuration (or lack thereof)
 */
public final class ServiceAuthorizationCheck extends AbstractAction {

	@NotNull
	private final ServicesManager servicesManager;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ServiceAuthorizationCheck(final ServicesManager servicesManager) {
		this.servicesManager = servicesManager;
	}

	@Override
	protected Event doExecute(final RequestContext context) throws Exception {
		final Service service = WebUtils.getService(context);
		//No service == plain /login request. Return success indicating transition to the login form
		if(service == null) {
			return success();
		}
		final RegisteredService registeredService = this.servicesManager.findServiceBy(service);

		if (registeredService == null) {
			logger.warn("Unauthorized Service Access for Service: [ {} ] - service is not defined in the service registry.", service.getId());
			throw new UnauthorizedServiceException();
		}
		else if (!registeredService.isEnabled()) {
			logger.warn("Unauthorized Service Access for Service: [ {} ] - service is not enabled in the service registry.", service.getId());
			throw new UnauthorizedServiceException();
		}

		return success();
	}
}
