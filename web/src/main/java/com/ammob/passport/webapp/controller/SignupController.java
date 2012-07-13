package com.ammob.passport.webapp.controller;

import com.ammob.passport.Constants;
import com.ammob.passport.service.RoleManager;
import com.ammob.passport.webapp.form.UserForm;
import com.ammob.passport.webapp.util.AuthCodeUtil;
import com.ammob.passport.webapp.util.RequestUtil;
import com.ammob.passport.webapp.util.SecurityContext;
import com.ammob.passport.enumerate.StateEnum;
import com.ammob.passport.exception.UserExistsException;

import org.jasig.cas.CentralAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Controller to signup new users.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/signup*")
public class SignupController extends BaseFormController {
	
	private RoleManager roleManager;
	
	private CentralAuthenticationService centralAuthenticationService;
	
	private CookieGenerator ticketGrantingTicketCookieGenerator;

	@Autowired
	public void setRoleManager(RoleManager roleManager) {
		this.roleManager = roleManager;
	}
	
	@Autowired
	public void setCentralAuthenticationService(CentralAuthenticationService centralAuthenticationService) {
		this.centralAuthenticationService = centralAuthenticationService;
	}
	
	@Autowired
	public void setTicketGrantingTicketCookieGenerator(CookieGenerator ticketGrantingTicketCookieGenerator) {
		this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public UserForm showForm() {
		return new UserForm(true);
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET, params =  {"bind"})
	public ModelAndView showBindForm(HttpServletRequest request, WebRequest webRequest) {
		Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);
		if (connection != null) {
			UserForm userForm = UserForm.fromProviderUserProfile(connection.fetchUserProfile());
			userForm.setAvataUrl(connection.getImageUrl());
			userForm.setProviderId(StringUtils.capitalize(connection.getKey().getProviderId()));
			saveMessage(request, getText("user.bound", userForm.getUsername(),  request.getLocale()));
			saveMessage(request, getText("user.bound.tip", userForm.getProviderId(),  request.getLocale()));
			return new ModelAndView("bind", "userForm",  userForm);
		}
		saveError(request, getText("bind.error", "",  request.getLocale()));
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(method = RequestMethod.POST, params =  {"bind", "step!=1"})
	public String onBindSubmit(UserForm userForm, BindingResult errors,
			WebRequest webRequest, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		try {
			if(StringUtils.hasText(userForm.getConfirmPassword())) { // New User, Signup !
				onSubmit(userForm, errors, request, response);
			} else {
				SecurityContext.addCasSignin(centralAuthenticationService, ticketGrantingTicketCookieGenerator, userForm.getUsername(), userForm.getPassword(), false, false, response);
			}
			ProviderSignInUtils.handlePostSignUp(userForm.getUsername(), webRequest);
			return "redirect:/";
		} catch (Exception e) {
			e.fillInStackTrace();
			log.warn(e.getMessage());
			saveError(request, getText("bind.error.signup", request.getLocale()));
			return "signup";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(UserForm userForm, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		if (validator != null) { // validator is null during testing
			validator.validate(userForm, errors);
			if(request.getParameter(Constants.SECURITY_SUPERVISION_CODE) == null) {// don't validate when supervision
				if(!validateCaptcha(request)) {
					errors.rejectValue("captcha", "errors.captcha", new Object[] {}, "captcha error");
				}
				if (errors.hasErrors()) {
					return "signup";
				}
			}
		}
		Locale locale = request.getLocale();
		// Set the default user role on this new user
		userForm.addRole(roleManager.getRole(Constants.USER_ROLE));
		try {
			this.getUserManager().savePerson(userForm);
		} catch (UserExistsException e) {
			if(e.isContainsType(StateEnum.USERNAME_EXISTENCE)) 
				errors.rejectValue("username", "errors.existing.user", new Object[] { userForm.getUsername()}, "duplicate user");
			if(e.isContainsType(StateEnum.EMAIL_EXISTENCE))
				errors.rejectValue("email", "errors.existing.email", new Object[] {userForm.getEmail() }, "duplicate user email");
			userForm.setPassword(userForm.getConfirmPassword());// redisplay the unencrypted passwords
			return "signup";
		} catch (Exception e) {
			log.warn(e.getMessage());
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		saveMessage(request, getText("user.registered", userForm.getUsername(), locale));
		request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);
		// log user in automatically
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				userForm.getUsername(), userForm.getConfirmPassword(), userForm.getAuthorities());
		auth.setDetails(userForm);
		SecurityContextHolder.getContext().setAuthentication(auth);
		// cas
		SecurityContext.addCasSignin(centralAuthenticationService, ticketGrantingTicketCookieGenerator, userForm.getUsername(), userForm.getConfirmPassword(), true, false, response);
		   
		// Send user an e-mail
		if (log.isDebugEnabled()) {
			log.debug("Sending user '" + userForm.getUsername() + "' an account information e-mail");
		}
		// Send an account information e-mail
		message.setSubject(getText("signup.email.subject", locale));
		try {
			RequestUtil.setCookie(response, Constants.STATES_EMAIL_VERIFIED, Long.toString(System.currentTimeMillis()), "/");
			sendUserMessage(userForm, getText("signup.email.message", locale), RequestUtil.getAppURL(request) + "/hint?" + AuthCodeUtil.wrap(userForm.getUsername()) + "&activation");
		} catch (MailException me) {
			saveError(request, me.getMostSpecificCause().getMessage());
		}
	    return getRedirectView("/login", request.getParameter("service"));
	}
}
