package com.ammob.passport.webapp.controller;

import com.ammob.passport.Constants;
import com.ammob.passport.service.RoleManager;
import com.ammob.passport.webapp.form.SignupForm;
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
	public SignupForm showForm() {
		return new SignupForm(true);
	}

	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET, params =  {"bind"})
	public ModelAndView showBindForm(HttpServletRequest request, WebRequest webRequest) {
		Connection<?> connection = ProviderSignInUtils.getConnection(webRequest);
		if (connection != null) {
			SignupForm signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
			signupForm.setAvataUrl(connection.getImageUrl());
			signupForm.setProviderId(StringUtils.capitalize(connection.getKey().getProviderId()));
			saveMessage(request, getText("user.bound", signupForm.getUsername(),  request.getLocale()));
			saveMessage(request, getText("user.bound.tip", signupForm.getProviderId(),  request.getLocale()));
			return new ModelAndView("bind", "signupForm",  signupForm);
		}
		saveError(request, getText("bind.error", "",  request.getLocale()));
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(method = RequestMethod.POST, params =  {"bind", "step!=1"})
	public String onBindSubmit(SignupForm signupForm, BindingResult errors,
			WebRequest webRequest, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		try {
			if(StringUtils.hasText(signupForm.getConfirmPassword())) { // New User, Signup !
				onSubmit(signupForm, errors, request, response);
			} else {
				SecurityContext.addCasSignin(centralAuthenticationService, ticketGrantingTicketCookieGenerator, signupForm.getUsername(), signupForm.getPassword(), false, false, response);
			}
			ProviderSignInUtils.handlePostSignUp(signupForm.getUsername(), webRequest);
			return "redirect:/";
		} catch (Exception e) {
			saveError(request, getText("bind.error.signup", new Object[] {},  request.getLocale()));
			return "signup";
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(SignupForm signupForm, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		if (validator != null) { // validator is null during testing
			validator.validate(signupForm, errors);
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
		signupForm.addRole(roleManager.getRole(Constants.USER_ROLE));
		try {
			this.getUserManager().savePerson(signupForm);
		} catch (UserExistsException e) {
			if(e.isContainsType(StateEnum.USERNAME_EXISTENCE)) 
				errors.rejectValue("username", "errors.existing.user", new Object[] { signupForm.getUsername()}, "duplicate user");
			if(e.isContainsType(StateEnum.EMAIL_EXISTENCE))
				errors.rejectValue("email", "errors.existing.email", new Object[] {signupForm.getEmail() }, "duplicate user email");
			signupForm.setPassword(signupForm.getConfirmPassword());// redisplay the unencrypted passwords
			return "signup";
		} catch (Exception e) {
			log.warn(e.getMessage());
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		saveMessage(request, getText("user.registered", signupForm.getUsername(), locale));
		request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);
		// log user in automatically
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				signupForm.getUsername(), signupForm.getConfirmPassword(), signupForm.getAuthorities());
		auth.setDetails(signupForm);
		SecurityContextHolder.getContext().setAuthentication(auth);
		// cas
		SecurityContext.addCasSignin(centralAuthenticationService, ticketGrantingTicketCookieGenerator, signupForm.getUsername(), signupForm.getConfirmPassword(), true, false, response);
		   
		// Send user an e-mail
		if (log.isDebugEnabled()) {
			log.debug("Sending user '" + signupForm.getUsername() + "' an account information e-mail");
		}
		// Send an account information e-mail
		message.setSubject(getText("signup.email.subject", locale));
		try {
			sendUserMessage(signupForm, getText("signup.email.message", locale), RequestUtil.getAppURL(request));
		} catch (MailException me) {
			saveError(request, me.getMostSpecificCause().getMessage());
		}
	   return getRedirectView("/login", signupForm.getService());
	}
}
