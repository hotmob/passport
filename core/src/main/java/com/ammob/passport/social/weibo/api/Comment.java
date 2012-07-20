/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @author iday
 * 
 */
public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877707548084527830L;

	private final long id;
	private final Date createAt;
	private final String text;
	private final String source;
	private final WeiboProfile user;
	private final Tweet status;
	private final Comment replyComment;

	/**
	 * @param id
	 * @param createAt
	 * @param text
	 * @param source
	 * @param user
	 * @param status
	 * @param replyComment
	 */
	public Comment(long id, Date createAt, String text, String source,
			WeiboProfile user, Tweet status, Comment replyComment) {
		this.id = id;
		this.createAt = createAt;
		this.text = text;
		this.source = source;
		this.user = user;
		this.status = status;
		this.replyComment = replyComment;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the createAt
	 */
	public Date getCreateAt() {
		return createAt;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @return the user
	 */
	public WeiboProfile getUser() {
		return user;
	}

	/**
	 * @return the status
	 */
	public Tweet getStatus() {
		return status;
	}

	/**
	 * @return the replyComment
	 */
	public Comment getReplyComment() {
		return replyComment;
	}

}
