package com.ammob.passport.webapp.controller;

import com.ammob.passport.Constants;
import com.ammob.passport.enumerate.AttributeEnum;
import com.ammob.passport.webapp.form.HintForm;

import org.jasig.services.persondir.IPersonAttributes;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.util.Locale;

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
	public HintForm showForm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String username = request.getParameter("username");
		String authcode = request.getParameter("authcode");
		String timestamp = request.getParameter("timestamp");
		log.info(username + authcode + timestamp);
		if(StringUtils.hasText(username) && StringUtils.hasText(authcode) && StringUtils.hasText(timestamp)) {
			Locale locale = request.getLocale();
			HintForm user = new HintForm(2);
			//authcode = AuthCode.authcodeDecode(URLDecoder.decode(authcode, "UTF-8"), Constants.SECURITY_SUPERVISION_CODE); // TODO
			log.info(authcode);
			if((username.concat(timestamp)).equals(authcode)) {
				try {
					Long mistiming = 1L;//AuthCode.getUnixTimestamp() - Long.valueOf(timestamp);// TODO
					log.info("Long mistiming value : " + mistiming);
					if(mistiming < 36000) {
						user.setUsername(username);
					} else {
						saveError(request, this.getText("errors.exceed.time", new Object[] {"URL"}, locale));
					}
				} catch (Exception e) {
					saveError(request, this.getText("errors.conversion", new Object[] {}, locale));
				}
			} else {
				saveError(request, this.getText("errors.token", new Object[] {}, locale));
			}
			return user;
		}
		return new HintForm();
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(HintForm hintForm, BindingResult errors,
			HttpServletRequest request, HttpServletResponse response)
				throws Exception {
    	if (validator != null) { // validator is null during testing
			validator.validate(hintForm, errors);
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
		switch(hintForm.getStep()) {
		case 0:
			try {
				IPersonAttributes user = this.getUserManager().getPersonAttributes(hintForm.getUsername());
				hintForm.setStep(1);
				hintForm.setUsername(user.getAttributeValue(AttributeEnum.USER_USERNAME.getValue()).toString());
				hintForm.setEmail(user.getAttributeValue(AttributeEnum.USER_EMAIL.getValue()).toString());
		        Locale locale = request.getLocale();
		        message.setSubject(getText("hint.email.subject", locale));
				try {
					String unixTimestamp = String.valueOf(System.currentTimeMillis());
					String authCode = "";//AuthCode.authcodeEncode(user.getUsername().concat(unixTimestamp), Constants.SECURITY_SUPERVISION_CODE);// TODO
					log.info(authCode);
					String url = "http://passport.766.com/hint?username=" + hintForm.getUsername() + "&timestamp=" + unixTimestamp + "&authcode=" + URLEncoder.encode(authCode, "UTF-8");// RequestUtil.getAppURL(request);
					sendUserMessage(hintForm, authCode, url);
					//saveMessage(request, this.getText("hint.sent", new Object[] { user.getUsername(), user.getEmail() }, locale));
				} catch (MailException me) {
					//saveError(request, me.getMostSpecificCause().getMessage());
				}
			} catch (UsernameNotFoundException e) {
				errors.rejectValue("username", "hint.error", new Object[] {hintForm.getUsername()}, "errors user identifying");
				return "hint";
			} catch (Exception e) {
				e.printStackTrace();
				log.warn(e.getMessage());
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			break;
		case 2:
			hintForm.setStep(3);
			return "hint";
		default:
			return "home";
		}
        return "hint";
//        return new org.springframework.web.servlet.view.RedirectView(request.getContextPath()).getUrl();
    }
}
