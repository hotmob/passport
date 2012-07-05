package com.ammob.passport.webapp.controller;

import com.ammob.passport.Constants;
import com.ammob.passport.enumerate.AttributeEnum;
import com.ammob.passport.model.User;
import com.ammob.passport.webapp.form.UserForm;
import com.ammob.passport.webapp.util.AuthCodeUtil;
import com.ammob.passport.webapp.util.RequestUtil;

import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple class to retrieve and send a password hint to users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/hint*")
public class HintController extends BaseFormController {
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET)
	public UserForm showForm(){
		return new UserForm();
	}
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET, params =  {"username", "authcode", "timestamp", "activation"})
	public UserForm showForm(@RequestParam(value="username", required=true) String username, 
			 								 @RequestParam(value="authcode", required=true) String authcode, 
			 								 @RequestParam(value="timestamp", required=true) Long timestamp, HttpServletRequest request) throws Exception {
		if((username.concat(timestamp.toString())).equals(AuthCodeUtil.authcodeDecode(authcode))) {
			User user = this.getUserManager().getUserByUsername(username);
			user.setState(Constants.STATES_EMAIL_VERIFIED);
			this.getUserManager().savePerson(user);
			return new UserForm(username, 100);
		} else {
			saveError(request, this.getText("errors.token", new Object[] {}, request.getLocale()));
			log.warn("user name, pw and auth : " + username + " : " + timestamp + " : " + authcode + " : " + AuthCodeUtil.authcodeDecode(authcode));
		}
		return new UserForm(username, 100);
	}
	
	@ModelAttribute
	@RequestMapping(method = RequestMethod.GET, params =  {"username", "authcode", "timestamp", "password"})
	public UserForm showForm(@RequestParam(value="username", required=true) String username, 
											 @RequestParam(value="authcode", required=true) String authcode, 
											 @RequestParam(value="timestamp", required=true) Long timestamp, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if((username.concat(timestamp.toString())).equals(AuthCodeUtil.authcodeDecode(authcode))) {
			Long mistiming = AuthCodeUtil.getUnixTimestamp() - timestamp;
			log.info("Long mistiming value : " + mistiming);
			if(mistiming < 3600) {
				IPersonAttributes attributes = this.getUserManager().getPersonAttributes(username);
				return new UserForm(username, new String((byte[]) attributes.getAttributeValue(AttributeEnum.USER_PASSWORD.getValue())), attributes.getAttributeValue(AttributeEnum.USER_EMAIL.getValue()).toString(), 2);
			} else {
				saveError(request, this.getText("errors.exceed.time", new Object[] {"URL"}, request.getLocale()));
			}
		} else {
			saveError(request, this.getText("errors.token", new Object[] {}, request.getLocale()));
			log.warn("user name, pw and auth : " + username + " : " + timestamp + " : " + authcode + " : " + AuthCodeUtil.authcodeDecode(authcode));
		}
		return new UserForm(username, 2);
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(UserForm userForm, BindingResult errors, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (validator != null) { // validator is null during testing
			validator.validate(userForm, errors);
			if(request.getParameter(Constants.SECURITY_SUPERVISION_CODE) == null) {// don't validate when supervision
				if(!validateCaptcha(request)) {
					errors.rejectValue("captcha", "errors.captcha", new Object[] {}, "captcha error");
				}
				if (errors.hasErrors()) {
					return "hint";
				}
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("Processing Password Hint...");
		}
		switch(userForm.getStep()) {
		case 0:
			try {
				userForm.setStep(1);
				IPersonAttributes user = this.getUserManager().getPersonAttributes(userForm.getUsername());
				userForm.setEmail(user.getAttributeValue(AttributeEnum.USER_EMAIL.getValue()).toString());
		        message.setSubject(getText("hint.email.subject", request.getLocale()));
				String url = RequestUtil.getAppURL(request) + "/hint?" + AuthCodeUtil.wrap(userForm.getUsername()) + "&password";
				sendUserMessage(userForm, getText("hint.heading", request.getLocale()), url);
			} catch (UsernameNotFoundException e) {
				errors.rejectValue("username", "hint.error", new Object[] {userForm.getUsername()}, "errors user identifying");
			} catch (MailException me) {
				saveError(request, me.getMostSpecificCause().getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				log.warn(e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			break;
		case 2:
			try {
				userForm.setStep(3);
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userForm.getUsername(), null, null));
				this.getUserManager().changePassword(userForm.getOldPassword(), userForm.getPassword());
				SecurityContextHolder.getContext().setAuthentication(null);
			} catch (Exception e) {
				e.printStackTrace();
				log.warn(e.getMessage());
				saveError(request, e.getMessage());
			}
			break;
		default:
			return "home";
		}
        return "hint";
    }
    
    @RequestMapping(method = RequestMethod.POST, params =  {"username", "activation"})
    public String onSubmit(@RequestParam(value="username", required=true) String username, HttpServletRequest request, HttpServletResponse response) throws Exception {
		message.setSubject(getText("signup.email.subject", request.getLocale()));
		try {
			User user = this.getUserManager().getUserByUsername(username);
			if(!user.getState().contains(Constants.STATES_EMAIL_VERIFIED))
				sendUserMessage(user, getText("signup.email.message", request.getLocale()), RequestUtil.getAppURL(request) + "/hint?" + AuthCodeUtil.wrap(username) + "&activation");
			RequestUtil.setCookie(response, Constants.STATES_EMAIL_VERIFIED, Long.toString(System.currentTimeMillis()), "/");
		} catch (MailException me) {
			saveError(request, me.getMostSpecificCause().getMessage());
		}
		return "redirect:/home";
    }
}
