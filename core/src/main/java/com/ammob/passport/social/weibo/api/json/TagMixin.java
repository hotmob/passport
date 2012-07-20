/**
 * 
 */
package com.ammob.passport.social.weibo.api.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author iday
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class TagMixin {
	
	@JsonCreator
	public TagMixin(
			@JsonProperty("id") long id,
			@JsonProperty("tag") String tag,
			@JsonProperty("count") int count) {}

}
