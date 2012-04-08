package com.ammob.passport.webapp.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.MultiTypeCaptchaService;

/**
 * Generates captcha image to tell whether its user is a human or a computer.
 * See http://forge.octo.com/jcaptcha/confluence/display/general/Home
 */
@Controller
public class CaptchaController {
	public static final String CAPTCHA_IMAGE_FORMAT = "jpeg";

	@Autowired
	private MultiTypeCaptchaService imageCaptchaService;

	@RequestMapping("/captcha*")
	public void showForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// get the session id that will identify the generated captcha.
			// the same id must be used to validate the response, the session id
			// is a good candidate!

			String captchaId = request.getSession().getId();
			BufferedImage challenge = imageCaptchaService.getImageChallengeForID(
					captchaId, request.getLocale());

			ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, jpegOutputStream);
			// ImageIO.write(challenge, CAPTCHA_IMAGE_FORMAT, new java.io.File("F:\\002_new.JPG")); // Test Out Image
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/" + CAPTCHA_IMAGE_FORMAT);
	        
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}
	
/*	protected void validateCaptcha(RegistrationForm registrationForm, BindingResult result, String sessionId, String errorCode) {
		  // If the captcha field is already rejected
		  if (null != result.getFieldError("captcha")) return;
		  boolean validCaptcha = false;
		  try {
		   validCaptcha = captchaService.validateResponseForID(sessionId, registrationForm.getCaptcha());
		  }
		  catch (CaptchaServiceException e) {
		   //should not happen, may be thrown if the id is not valid
		   logger.warn("validateCaptcha()", e);
		  }
		  if (!validCaptcha) {
		   result.rejectValue("captcha", errorCode);
		  }
		 }*/
}