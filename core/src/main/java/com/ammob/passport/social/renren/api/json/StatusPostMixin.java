/**
 * 
 */
package com.ammob.passport.social.renren.api.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.ammob.passport.social.renren.api.Place;
import com.ammob.passport.social.renren.api.Reference;
import com.ammob.passport.social.renren.api.RenrenProfile;
import com.ammob.passport.social.renren.api.Source;

/**
 * @author iday
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class StatusPostMixin {

	/**
	 * 
	 */
	@JsonCreator
	public StatusPostMixin(
			@JsonProperty("status_id") long id,
			@JsonProperty("uid") @JsonDeserialize(using = UserDeserializer.class) Reference user,
			@JsonDeserialize(using = SourceDeserializer.class) Source source,
			@JsonProperty("view_count") long viewCount,
			@JsonProperty("comment_count") long commentCount) {
	}
	
	@JsonProperty("place")
	public Place place;
	
	

	private static class UserDeserializer extends
			JsonDeserializer<RenrenProfile> {

		@Override
		public RenrenProfile deserialize(JsonParser jp,
				DeserializationContext ctxt) throws IOException,
				JsonProcessingException {
			
			return null;
		}

	}

	private static class SourceDeserializer extends JsonDeserializer<Source> {
		private Source source = new Source();
		
		@Override
		public Source deserialize(JsonParser jp, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			String nv = jp.nextTextValue();
			return source;
		}
	}

}
