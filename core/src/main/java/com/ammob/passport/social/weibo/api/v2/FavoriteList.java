/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ammob.passport.social.weibo.api.Favorite;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoriteList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4835982037524937456L;
	private final List<Favorite> favorites;
	private final long totalNumber;
	
	/**
	 * @param favorites
	 * @param totalNumber
	 */
	@JsonCreator
	public FavoriteList(
			@JsonProperty("favorites") List<Favorite> favorites,
			@JsonProperty("total_number") long totalNumber) {
		this.favorites = favorites;
		this.totalNumber = totalNumber;
	}

	/**
	 * @return the favorites
	 */
	public List<Favorite> getFavorites() {
		return favorites;
	}

	/**
	 * @return the totalNumber
	 */
	public long getTotalNumber() {
		return totalNumber;
	}

}
