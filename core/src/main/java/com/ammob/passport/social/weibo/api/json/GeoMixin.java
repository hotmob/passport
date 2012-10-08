package com.ammob.passport.social.weibo.api.json;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class GeoMixin {
	
	@JsonCreator
	public GeoMixin(
			@JsonProperty("latitude") double latitude,
			@JsonProperty("longitude") double longitude) {}
	
	@JsonProperty("city") private int city;
	@JsonProperty("province") private int province;
	@JsonProperty("cityName") private String cityName;
	@JsonProperty("provinceName") private String provinceName;
	@JsonProperty("address") private String address;
	@JsonProperty("pinyin") private String pinyin;
	@JsonProperty("more") private String more;
}
