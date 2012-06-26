package com.ammob.passport.webapp.form;

public class HintForm extends SignupForm  {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	private String oldPassword;
	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public HintForm() {
		
	}
	
	/**
	 * constructor - creates a new instance with set enabled.
	 * @param enabled
	 */
	public HintForm(int step){
		this.setStep(step);
	}
	
	public HintForm(String username, int step){
		this.setUsername(username);
		this.setStep(step);
	}
	
	public HintForm(String username, String password, int step){
		this.setUsername(username);
		this.setOldPassword(password);
		this.setStep(step);
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
}
