package com.ammob.passport.webapp.controller;

import java.util.List;

import com.ammob.passport.Constants;
import com.ammob.passport.model.User;
import com.ammob.passport.service.UserManager;
import com.ammob.passport.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.control.PagedResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/admin/users*")
public class UserController {
    private UserManager mgr = null;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.mgr = userManager;
    }

	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query, @RequestParam(required = false, value = "c") String current) throws Exception {
    	ModelAndView mav = new ModelAndView("admin/userList");
		if(StringUtil.hasText(query)){
			List<User> users = mgr.search(query);
			try {users.add(mgr.getUserByUsername(query));} catch (Exception e) {}
			mav.addObject(Constants.USER_LIST, users);
		} else {
			int _current = 0;
			try {_current = Integer.parseInt(current);} catch (Exception e) {}
			try {
				PagedResult pr = mgr.getPersons(_current, 20);
				mav.addObject(Constants.USER_LIST, pr.getResultList());
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.addObject("c", _current + 1);
		}
        return mav;
    }
}
