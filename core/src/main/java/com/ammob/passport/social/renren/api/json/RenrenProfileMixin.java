/**
 * 
 */
package com.ammob.passport.social.renren.api.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author iday
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class RenrenProfileMixin {

	@JsonCreator
	RenrenProfileMixin(
			@JsonProperty("uid") String id,
			@JsonProperty("name") String name,
			@JsonProperty("sex") String sex,
			@JsonProperty("headurl") String headurl) {}
}
