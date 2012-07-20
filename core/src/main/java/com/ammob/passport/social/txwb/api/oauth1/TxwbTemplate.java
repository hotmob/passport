/**
 * 
 */
package com.ammob.passport.social.txwb.api.oauth1;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.util.MultiValueMap;

import com.ammob.passport.social.txwb.api.Txwb;
import com.ammob.passport.social.txwb.api.UserOperations;
import com.ammob.passport.social.txwb.api.json.TxwbObjectMapper;
import com.ammob.passport.social.txwb.connect.oauth1.TxwbOauth1Support;

/**
 * @author iday
 * 
 */
public class TxwbTemplate extends AbstractOAuth1ApiBinding implements Txwb {
	private UserOperations userOperations;
	private ObjectMapper objectMapper;
	private TxwbOauth1Support oauth1Support;
	private final String consumerKey;
	private final String consumerSecret;
	private final String accessTokenSecret;
	private final String accessToken;

	/**
	 * 
	 */
	public TxwbTemplate() {
		super();
		initialize();
		this.consumerKey = null;
		this.consumerSecret = null;
		this.accessTokenSecret = null;
		this.accessToken = null;
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 */
	public TxwbTemplate(String consumerKey, String consumerSecret,
			String accessToken, String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		initialize();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.accessTokenSecret = accessTokenSecret;
		this.accessToken = accessToken;
	}

	private void initialize() {
		oauth1Support = new TxwbOauth1Support();
		userOperations = new UserTemplate(this, getRestTemplate(),
				isAuthorized());
	}

	@Override
	public MultiValueMap<String, String> commonOAuthParameters(
			HttpMethod method, String targetUrl,
			MultiValueMap<String, String> additionalParameters) {
		additionalParameters.add("oauth_token", accessToken);
		return oauth1Support.commonOAuthParameters(consumerKey, method, targetUrl, additionalParameters, consumerSecret, accessTokenSecret);
	}

	/**
	 * @return the userOperations
	 */
	@Override
	public UserOperations userOperations() {
		return userOperations;
	}

	@Override
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		MappingJacksonHttpMessageConverter converter = super
				.getJsonMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.addAll(converter.getSupportedMediaTypes());
		list.add(MediaType.TEXT_HTML);
		converter.setSupportedMediaTypes(list);

		objectMapper = new TxwbObjectMapper();
		converter.setObjectMapper(objectMapper);
		return converter;
	}

}
