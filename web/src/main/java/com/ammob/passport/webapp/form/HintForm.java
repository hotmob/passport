package com.ammob.passport.webapp.form;

public class HintForm extends SignupForm  {

	private static final long serialVersionUID = 1L;
	private int step = 0;
	
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
		setStep(step);
	}
	
	public int getStep() {
		return step;
	}
	
	public void setStep(int step) {
		this.step = step;
	}
}
