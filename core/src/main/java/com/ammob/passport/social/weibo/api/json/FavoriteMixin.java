/**
 * 
 */
package com.ammob.passport.social.weibo.api.json;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.ammob.passport.social.weibo.api.Tag;
import com.ammob.passport.social.weibo.api.TimelineDateDeserializer;
import com.ammob.passport.social.weibo.api.Tweet;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class FavoriteMixin {
	
	@JsonCreator
	public FavoriteMixin(
			@JsonProperty("status") Tweet status,
			@JsonProperty("tags") List<Tag> tags,
			@JsonProperty("favorited_time") @JsonDeserialize(using = TimelineDateDeserializer.class) Date favoritedTime) {}

}
