/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.ApiBinding;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.ammob.passport.social.renren.api.impl.UserTemplate;
import com.ammob.passport.social.renren.api.json.RenrenModule;

/**
 * @author iday
 * 
 */
public class Renren extends AbstractOAuth2ApiBinding implements ApiBinding {

	private UserOperations userOperations;
	private final String accessToken;
	private final String clientSecret;
	private ObjectMapper objectMapper;

	/**
	 * 
	 */
	public Renren() {
		super();
		this.accessToken = null;
		this.clientSecret = null;
		initialize();
	}

	/**
	 * @param accessToken
	 */
	public Renren(String accessToken, String clientSecret) {
		super(accessToken);
		this.clientSecret = clientSecret;
		this.accessToken = accessToken;
		initialize();
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return this.accessToken;
	}

	/**
	 * @return the clientSecret
	 */
	public String getClientSecret() {
		return this.clientSecret;
	}

	/**
	 * @return the userOperations
	 */
	public UserOperations getUserOperations() {
		return userOperations;
	}

	// private helpers
	private void initialize() {
		getRestTemplate().setErrorHandler(new DefaultResponseErrorHandler());
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		userOperations = new UserTemplate(this, getRestTemplate(),
				objectMapper, isAuthorized());
	}
	
	

	/* (non-Javadoc)
	 * @see org.springframework.social.oauth2.AbstractOAuth2ApiBinding#getJsonMessageConverter()
	 */
	@Override
	protected MappingJacksonHttpMessageConverter getJsonMessageConverter() {
		MappingJacksonHttpMessageConverter converter = super
				.getJsonMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.addAll(converter.getSupportedMediaTypes());
		list.add(MediaType.TEXT_PLAIN);
		converter.setSupportedMediaTypes(list);

		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new RenrenModule());
		converter.setObjectMapper(objectMapper);
		return converter;
	}

}
