/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class Reference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4753401723413786718L;

	private final Long id;
	private final String name;

	/**
	 * @param id
	 * @param name
	 */
	public Reference(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
