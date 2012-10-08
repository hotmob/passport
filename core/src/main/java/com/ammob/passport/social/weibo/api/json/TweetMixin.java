package com.ammob.passport.social.weibo.api.json;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.ammob.passport.social.weibo.api.Geo;
import com.ammob.passport.social.weibo.api.TimelineDateDeserializer;
import com.ammob.passport.social.weibo.api.WeiboProfile;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class TweetMixin {

	@JsonCreator
	public TweetMixin(
			@JsonProperty("created_at") @JsonDeserialize(using = TimelineDateDeserializer.class) Date createdAt,
			@JsonProperty("id") long id,
			@JsonProperty("text") String text,
			@JsonProperty("source") String source,
			@JsonProperty("favorited") boolean favorited,
			@JsonProperty("truncated") boolean truncated,
			@JsonProperty("in_reply_to_status_id") long inReplyToStatusId,
			@JsonProperty("in_reply_to_user_id") long inReplyToUserId,
			@JsonProperty("in_reply_to_screen_name") String inReplyToScreenName,
			@JsonProperty("user") WeiboProfile user,
			@JsonProperty("repostsCount") int repostsCount,
			@JsonProperty("commentsCount") int commentsCount) {
	}
	
	@JsonProperty("geo") private Geo geo;

}
