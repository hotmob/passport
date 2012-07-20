package com.ammob.passport.social.renren.api;

import java.io.Serializable;

public abstract class Post implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8792227170298803389L;

	private final long id;
	private final Reference user;
	private final long viewCount;
	private final long commentCount;

	/**
	 * @param id
	 * @param user
	 * @param source
	 * @param viewCount
	 * @param commentCount
	 */
	public Post(long id, Reference user, Source source, long viewCount,
			long commentCount) {
		this.id = id;
		this.user = user;
		this.viewCount = viewCount;
		this.commentCount = commentCount;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the user
	 */
	public Reference getUser() {
		return user;
	}

	/**
	 * @return the viewCount
	 */
	public long getViewCount() {
		return viewCount;
	}

	/**
	 * @return the commentCount
	 */
	public long getCommentCount() {
		return commentCount;
	}

}