package com.ammob.passport.webapp.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ammob.passport.Constants;
import com.ammob.passport.model.Role;
import com.ammob.passport.model.User;
import com.ammob.passport.webapp.form.UserForm;
import com.ammob.passport.webapp.util.RequestUtil;

@Controller
@RequestMapping("/home*")
public class HomeController extends BaseFormController {
	
    public HomeController() {
        setCancelView("redirect:/home");
        setSuccessView("redirect:/home");
    }
    
    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected UserForm showForm(HttpServletRequest request, HttpServletResponse response)
    		throws Exception {
    	try {
    		User person = this.getUserManager().getUserByUsername(request.getRemoteUser());
    		if(!person.getRoles().contains(new Role(Constants.ADMIN_ROLE))){ // temp settings !
    			response.sendRedirect("http://safe.766.com");
    		} else {
    			UserForm user = UserForm.fromProviderUser(person);
    			Cookie cookie = RequestUtil.getCookie(request, Constants.STATES_EMAIL_VERIFIED);
    			if(cookie != null) {
    				Long timeStamp = null, currentTimeStamp = System.currentTimeMillis();
    				try {timeStamp = Long.parseLong(cookie.getValue());} catch (Exception e) {}
    				if(timeStamp != null && currentTimeStamp - timeStamp < 30000)
    					user.setSend(true);
    				log.info(" email cookie ! timeStamp : " + timeStamp + ", " + cookie.getValue() + ", " + currentTimeStamp);
    			}
    			return user;
    		}
    	} catch (UsernameNotFoundException e) {
    		log.warn("Error : User is not found ! [ " + request.getRemoteUser() + " ]");
    	}
    	return new UserForm(request.getRemoteUser());
    }
}
