/**
 * 
 */
package com.ammob.passport.social.weibo.api.json;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.ammob.passport.social.weibo.api.Comment;
import com.ammob.passport.social.weibo.api.TimelineDateDeserializer;
import com.ammob.passport.social.weibo.api.Tweet;
import com.ammob.passport.social.weibo.api.WeiboProfile;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class CommentMixin {
	
	@JsonCreator
	public CommentMixin(
			@JsonProperty("id") long id,
			@JsonProperty("create_at") @JsonDeserialize(using = TimelineDateDeserializer.class) Date createAt,
			@JsonProperty("text") String text,
			@JsonProperty("source") String source,
			@JsonProperty("user") WeiboProfile user,
			@JsonProperty("status") Tweet status, 
			@JsonProperty("reply_comment") Comment replyComment) {}
}
