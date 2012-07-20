/**
 * 
 */
package com.ammob.passport.social.txwb.api.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class TxwbProfileMixin  {
	
	@JsonCreator
	public TxwbProfileMixin(
			@JsonProperty("name") String name,
			@JsonProperty("openid") String openId,
			@JsonProperty("nick") String nick,
			@JsonProperty("head") String head,
			@JsonProperty("location") String location,
			@JsonProperty("vip") boolean vip,
			@JsonProperty("ent") boolean ent,
			@JsonProperty("introduction") String introduction,
			@JsonProperty("verifyinfo") String verifyinfo,
			@JsonProperty("email") String email,
			@JsonProperty("sex") int sex,
			@JsonProperty("fnasnum") long fnasnum,
			@JsonProperty("idolnum") long idolnum,
			@JsonProperty("tweetnum") long tweetnum) {}
}
