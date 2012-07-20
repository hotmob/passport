package com.ammob.passport.webapp.form;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.social.connect.UserProfile;

import com.ammob.passport.model.User;

public class UserForm extends User  {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	private String oldPassword;
	private String service;
	private String providerId;
	private String captcha;
	private boolean isSend = false;
	
	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public UserForm() {
		setEnabled(true);
	}
	
	/**
	 * constructor - creates a new instance with set enabled.
	 * @param enabled
	 */
	public UserForm(boolean enabled){
		setEnabled(enabled);
	}
	
	/**
	 * constructor - creates a new instance with set enabled.
	 * @param enabled
	 */
	public UserForm(int step){
		this.setStep(step);
	}
	
	public UserForm(String username) {
		super(username);
	}
	
	public UserForm(String username, int step){
		this(username);
		this.setStep(step);
	}
	
	public UserForm(String username, String password, String email, int step){
		this(username, step);
		this.setEmail(email);
		this.setOldPassword(password);
	}
	
	public int getStep() {
		return step;
	}
	
	public void setStep(int step) {
		this.step = step;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getService() {
		return service;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}
	
	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * 是否发送验证邮件
	 * @return
	 */
	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}
	
	
	public static UserForm fromProviderUserProfile(UserProfile providerUser) {
		UserForm form = new UserForm();
		form.setFirstName(providerUser.getFirstName());
		form.setLastName(providerUser.getLastName());
		form.setDisplayName(providerUser.getUsername());
		form.setEmail(providerUser.getEmail());
		return form;
	}
	
	public static UserForm fromProviderUser(User providerUser) {
		UserForm form = new UserForm();
		try {
			BeanUtils.copyProperties(form, providerUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
}
