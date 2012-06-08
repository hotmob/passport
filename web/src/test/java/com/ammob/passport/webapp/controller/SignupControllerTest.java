package com.ammob.passport.webapp.controller;

import com.ammob.passport.Constants;
import com.ammob.passport.model.Address;
import com.ammob.passport.webapp.form.SignupForm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.subethamail.wiser.Wiser;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

public class SignupControllerTest extends BaseControllerTestCase {
    @Autowired
    private SignupController c = null;

    @Test
    public void testDisplayForm() throws Exception {
        assertNotNull(c.showForm());
    }

    @Test
    public void testSignupUser() throws Exception {
        MockHttpServletRequest request = newPost("/signup.html");
        request.addParameter(Constants.SECURITY_SUPERVISION_CODE, "test");
        
        Address address = new Address();
        address.setCity("Denver");
        address.setProvince("Colorado");
        address.setCountry("USA");
        address.setPostalCode("80210");

        SignupForm user = new SignupForm(true);
        user.setAddress(address);
        String time = String.valueOf(System.currentTimeMillis()).substring(2);
        user.setUsername("self".concat(time));
        user.setPassword("Password1");
        user.setConfirmPassword("Password1");
        user.setFirstName("First");
        user.setLastName("Last");
        user.setEmail(time.concat("self-registered@raibledesigns.com"));
        user.setWebsite("http://raibledesigns.com");
        user.setPasswordHint("Password is one with you.");
        user.setCaptcha("test");

        HttpServletResponse response = new MockHttpServletResponse();

        // start SMTP Server
        Wiser wiser = new Wiser();
        wiser.setPort(getSmtpPort());
        wiser.start();

        BindingResult errors = new DataBinder(user).getBindingResult();
        c.onSubmit(user, errors, request, response);
        assertFalse("errors returned in model", errors.hasErrors());

        // verify an account information e-mail was sent
        wiser.stop();
        
        assertTrue(wiser.getMessages().size() == 1);

        // verify that success messages are in the request
        assertNotNull(request.getSession().getAttribute("successMessages"));
        assertNotNull(request.getSession().getAttribute(Constants.REGISTERED));

        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
