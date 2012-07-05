package com.ammob.passport.webapp.controller;

import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.subethamail.wiser.Wiser;

import com.ammob.passport.webapp.form.UserForm;

import static org.junit.Assert.*;

public class HintControllerTest extends BaseControllerTestCase {
    @Autowired
    private HintController c = null;

    @Test
    public void testShowForm() throws Exception {
    	
    }
    
    @Ignore
    public void testExecute() throws Exception {
        MockHttpServletRequest request = newGet("/hint");
        request.addParameter("username", "mupeng");

       // start SMTP Server
        Wiser wiser = new Wiser();
        wiser.setPort(getSmtpPort());
        wiser.start();
        
        UserForm user = new UserForm(0);
        String time = String.valueOf(System.currentTimeMillis());
        user.setUsername("self".concat(time));
        user.setEmail(time.concat("self-registered@raibledesigns.com"));
        
        HttpServletResponse response = new MockHttpServletResponse();

        BindingResult errors = new DataBinder(user).getBindingResult();
        c.onSubmit(user, errors, request, response);
        
        // verify an account information e-mail was sent
        wiser.stop();
        assertTrue(wiser.getMessages().size() == 1);
        
        // verify that success messages are in the session
        assertNotNull(request.getSession().getAttribute(BaseFormController.MESSAGES_SUCESS_KEY));
    }
}
