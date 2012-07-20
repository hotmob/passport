/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author iday
 * 
 */
public class Favorite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6027316191945829826L;
	private final Tweet status;
	private final List<Tag> tags;
	private final Date favoritedTime;

	/**
	 * @param status
	 * @param tags
	 * @param favoritedTime
	 */
	public Favorite(Tweet status, List<Tag> tags, Date favoritedTime) {
		this.status = status;
		this.tags = tags;
		this.favoritedTime = favoritedTime;
	}

	/**
	 * @return the status
	 */
	public Tweet getStatus() {
		return status;
	}

	/**
	 * @return the tags
	 */
	public List<Tag> getTags() {
		return tags;
	}

	/**
	 * @return the favoritedTime
	 */
	public Date getFavoritedTime() {
		return favoritedTime;
	}

}
