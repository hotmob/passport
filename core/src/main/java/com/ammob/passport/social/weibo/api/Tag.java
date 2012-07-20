/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class Tag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101144518771173821L;
	private final long id;
	private final String tag;
	private final int count;

	/**
	 * @param id
	 * @param tag
	 */
	public Tag(long id, String tag, int count) {
		this.id = id;
		this.tag = tag;
		this.count = count;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

}
