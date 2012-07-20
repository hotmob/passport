/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class Source implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998239250404200243L;

	private String text;
	private String href;

	/**
	 * 
	 */
	public Source() {
	}

	/**
	 * @param text
	 * @param href
	 */
	public Source(String text, String href) {
		this.text = text;
		this.href = href;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "<a href=\"" + href + "\">" + text + "</a>";
	}

}
