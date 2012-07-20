/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ammob.passport.social.weibo.api.Comment;

/**
 * @author iday
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2376065887795762136L;

	private final List<Comment> comments;
	private final long previousCursor;
	private final long nextCursor;
	private final long totalNumber;

	/**
	 * @param comments
	 * @param previousCursor
	 * @param nextCursor
	 * @param totalNumber
	 */
	@JsonCreator
	public CommentList(
			@JsonProperty("comments") List<Comment> comments,
			@JsonProperty("previous_cursor") long previousCursor,
			@JsonProperty("next_cursor") long nextCursor,
			@JsonProperty("total_cumber") long totalNumber) {
		this.comments = comments;
		this.previousCursor = previousCursor;
		this.nextCursor = nextCursor;
		this.totalNumber = totalNumber;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @return the previousCursor
	 */
	public long getPreviousCursor() {
		return previousCursor;
	}

	/**
	 * @return the nextCursor
	 */
	public long getNextCursor() {
		return nextCursor;
	}

	/**
	 * @return the totalNumber
	 */
	public long getTotalNumber() {
		return totalNumber;
	}
}
