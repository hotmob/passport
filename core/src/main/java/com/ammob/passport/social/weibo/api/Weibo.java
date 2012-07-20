/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import org.springframework.social.ApiBinding;

import com.ammob.passport.social.weibo.api.UserOperations;

/**
 * @author iday
 *
 */
public interface Weibo extends ApiBinding {

	public abstract UserOperations userOperations();

	public abstract TimelineOperations timelineOperations();

	public abstract CommentOperations commentOperations();
	
}
