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
abstract class PlaceMixin {

	/**
	 * 
	 */
	@JsonCreator
	public PlaceMixin(
			@JsonProperty("id") long id,
			@JsonProperty("name") String name,
			@JsonProperty("address") String address,
			@JsonProperty("url") String url,
			@JsonProperty("longtitude") double longtitude,
			@JsonProperty("latitude") double latitude) {
	}

}
