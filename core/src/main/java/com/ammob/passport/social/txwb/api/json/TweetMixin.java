/**
 * 
 */
package com.ammob.passport.social.txwb.api.json;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.ammob.passport.social.txwb.api.Music;
import com.ammob.passport.social.txwb.api.Tweet;
import com.ammob.passport.social.txwb.api.TweetStatus;
import com.ammob.passport.social.txwb.api.Video;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class TweetMixin {
	
	@JsonCreator
	public TweetMixin(
			@JsonProperty("text") String text,
			@JsonProperty("orgtext") String orgtext,
			@JsonProperty("count") long count,
			@JsonProperty("mcount") long mcount,
			@JsonProperty("from") String from,
			@JsonProperty("fromurl") String fromurl,
			@JsonProperty("id") long id,
			@JsonProperty("image") String[] image,
			@JsonProperty("video") Video video,
			@JsonProperty("music") Music music,
			@JsonProperty("name") String name,
			@JsonProperty("openid") String openid,
			@JsonProperty("nick") String nick,
			@JsonProperty("self") boolean self,
			@JsonProperty("timestamp") Date timestamp,
			@JsonProperty("type") int type,
			@JsonProperty("head") String head,
			@JsonProperty("location") String location,
			@JsonProperty("countrycode") String countrycode,
			@JsonProperty("provincecode") String provincecode,
			@JsonProperty("citycode") String citycode,
			@JsonProperty("vip") boolean vip,
			@JsonProperty("geo") String geo,
			@JsonProperty("status") TweetStatus status,
			@JsonProperty("emotionurl") String emotionurl,
			@JsonProperty("emotiontype") String emotiontype,
			@JsonProperty("source") Tweet source) {};
}
