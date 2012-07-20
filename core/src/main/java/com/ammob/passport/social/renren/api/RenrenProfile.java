/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class RenrenProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7445071571826462845L;

	private final String id;
	private final String name;
	private final String sex;
	private final String headurl;

	/**
	 * @param id
	 * @param name
	 * @param sex
	 * @param headhurl
	 */
	public RenrenProfile(String id, String name, String sex, String headurl) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.headurl = headurl;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @return the headurl
	 */
	public String getHeadurl() {
		return headurl;
	}

}
