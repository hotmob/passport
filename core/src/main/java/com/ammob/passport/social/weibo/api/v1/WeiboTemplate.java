/**
 * 
 */
package com.ammob.passport.social.weibo.api.v1;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.weibo.api.CommentOperations;
import com.ammob.passport.social.weibo.api.TimelineOperations;
import com.ammob.passport.social.weibo.api.Weibo;
import com.ammob.passport.social.weibo.api.json.WeiboModule;

/**
 * @author iday
 * 
 */
public class WeiboTemplate extends AbstractOAuth1ApiBinding implements Weibo {
	private UserTemplate accountOperations;
	private ObjectMapper objectMapper;

	/**
	 * 
	 */
	public WeiboTemplate() {
		super();
		initialize();
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param accessToken
	 * @param accessTokenSecret
	 */
	public WeiboTemplate(String consumerKey, String consumerSecret, String accessToken,
			String accessTokenSecret) {
		super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		initialize();
	}

	private void initialize() {
		registerRenrenJsonModule(getRestTemplate());
		accountOperations = new UserTemplate(this, getRestTemplate(),
				isAuthorized());
	}

	/**
	 * @return the userOperations
	 */
	public UserTemplate userOperations() {
		return accountOperations;
	}

	private void registerRenrenJsonModule(RestTemplate restTemplate) {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new WeiboModule());
		List<HttpMessageConverter<?>> converters = getRestTemplate()
				.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}

	public TimelineOperations timelineOperations() {
		return null;
	}

	public CommentOperations commentOperations() {
		return null;
	}

}
