/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ammob.passport.social.weibo.api.WeiboProfile;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4871054765734757519L;
	
	private final List<WeiboProfile> users;
	private final long nextCursor;
	private final long previousCursor;
	private final long totalNumber;
	/**
	 * @param users
	 * @param nextCursor
	 * @param previousCursor
	 * @param totalNumber
	 */
	@JsonCreator
	public UserList(
			@JsonProperty("users") List<WeiboProfile> users,
			@JsonProperty("next_cursor") long nextCursor,
			@JsonProperty("previous_cursor") long previousCursor,
			@JsonProperty("total_number") long totalNumber) {
		this.users = users;
		this.nextCursor = nextCursor;
		this.previousCursor = previousCursor;
		this.totalNumber = totalNumber;
	}
	/**
	 * @return the users
	 */
	public List<WeiboProfile> getUsers() {
		return users;
	}
	/**
	 * @return the nextCursor
	 */
	public long getNextCursor() {
		return nextCursor;
	}
	/**
	 * @return the previousCursor
	 */
	public long getPreviousCursor() {
		return previousCursor;
	}
	/**
	 * @return the totalNumber
	 */
	public long getTotalNumber() {
		return totalNumber;
	}

}
