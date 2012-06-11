package com.ammob.passport.webapp.controller;

import org.apache.commons.lang.StringUtils;
import com.ammob.passport.Constants;
import com.ammob.passport.model.Role;
import com.ammob.passport.model.User;
import com.ammob.passport.service.RoleManager;
import com.ammob.passport.service.UserManager;
import com.ammob.passport.webapp.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/userform*")
public class UserFormController extends BaseFormController {
    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public UserFormController() {
        setCancelView("redirect:/home");
        setSuccessView("redirect:/userform");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(User user, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }
        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);
            if (errors.hasErrors() && request.getParameter(Constants.SECURITY_SUPERVISION_CODE) == null) { // don't validate when deleting
                return "userform";
            }
        }
        log.debug("entering 'onSubmit' method...");
        Locale locale = request.getLocale();
        if (request.getParameter(Constants.SECURITY_SUPERVISION_CODE) != null) {
            getUserManager().removeUser(user.getId().toString());
            saveMessage(request, getText("user.deleted", user.getDisplayName(), locale));
            return getSuccessView();
        } else {
            if (request.isUserInRole(Constants.ADMIN_ROLE)) {// only attempt to change roles if user is admin for other users, showForm() method will handle populating
                String[] userRoles = request.getParameterValues("userRoles");
                if (userRoles != null) {
                    user.getRoles().clear();
                    for (String roleName : userRoles) {
                        user.addRole(roleManager.getRole(roleName));
                    }
                }
            } else {// if user is not an admin then load roles from the database (or any other user properties that should not be editable by users without admin role) 
                User cleanUser = null;;
                try {
                	cleanUser = getUserManager().getPerson(request.getRemoteUser());
    			} catch (UsernameNotFoundException e) {
    				cleanUser = getUserManager().getUserByUsername(request.getRemoteUser());
    			}
                user.setRoles(cleanUser.getRoles());
            }
            Integer originalVersion = user.getVersion();
            try {
                getUserManager().savePerson(user);
            } catch (AccessDeniedException ade) { // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (Exception e) {
                errors.rejectValue("username", "errors.existing.user", new Object[]{user.getUsername()}, "duplicate user");
                user.setPassword(user.getConfirmPassword()); // redisplay the unencrypted passwords
                user.setVersion(originalVersion);  // reset the version # to what was passed in
                return "userform";
            }
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("user.saved", user.getDisplayName(), locale));
                // return to main Menu
                return getCancelView();
            } else {
                if (StringUtils.isBlank(request.getParameter("version"))) {
                    saveMessage(request, getText("user.added", user.getDisplayName(), locale));
                    // Send an account information e-mail
                    message.setSubject(getText("signup.email.subject", locale));
                    try {
                        sendUserMessage(user, getText("newuser.email.message", user.getDisplayName(), locale),
                                RequestUtil.getAppURL(request));
                    } catch (MailException me) {
                        saveError(request, me.getCause().getLocalizedMessage());
                    }
                    return getSuccessView();
                } else {
                    saveMessage(request, getText("user.updated.byAdmin", user.getDisplayName(), locale));
                }
            }
        }
        return "userform";
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected User showForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // If not an administrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) {
            if (isAdd(request) || request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                        request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            String userId = request.getParameter("id");

            // if user logged in with remember me, display a warning that they can't change passwords
            log.debug("checking for remember me login...");

            AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
            SecurityContext ctx = SecurityContextHolder.getContext();

            if (ctx.getAuthentication() != null) {
                Authentication auth = ctx.getAuthentication();

                if (resolver.isRememberMe(auth)) {
                    request.getSession().setAttribute("cookieLogin", "true");

                    // add warning message
                    saveMessage(request, getText("userProfile.cookieLogin", request.getLocale()));
                }
            }

            User user;
            if (userId == null && !isAdd(request)) {
                try {
					user = getUserManager().getPerson(request.getRemoteUser());
				} catch (UsernameNotFoundException e) {
					user = getUserManager().getUserByUsername(request.getRemoteUser());
				}
            } else if (!StringUtils.isBlank(userId) && !"".equals(request.getParameter("version"))) {
                user = getUserManager().getUser(userId);
            } else {
                user = new User();
                user.addRole(new Role(Constants.USER_ROLE));
            }
            user.setConfirmPassword(user.getPassword());
            return user;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return getUserManager().getUser(request.getParameter("id"));
        }
    }

    private boolean isFormSubmission(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(HttpServletRequest request) {
        String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
}
