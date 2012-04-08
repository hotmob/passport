package com.ammob.passport.webapp.controller;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CaptchaControllerTest extends BaseControllerTestCase {
    @Autowired
    private CaptchaController c = null;

	@Test
	public void test() {
		MockHttpServletRequest request = newGet("/captcha.jpeg");
		HttpServletResponse response = new MockHttpServletResponse();
		try {
			c.showForm(request, response);
			System.out.println(request.getAttribute("jcaptcha"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
