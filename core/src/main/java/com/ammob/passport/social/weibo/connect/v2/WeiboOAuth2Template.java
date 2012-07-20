/**
 * 
 */
package com.ammob.passport.social.weibo.connect.v2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

/**
 * @author iday
 *
 */
public class WeiboOAuth2Template extends OAuth2Template {

	public WeiboOAuth2Template(String clientId, String clientSecret,
			String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.oauth2.OAuth2Template#createRestTemplate()
	 */
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(2);
		converters.add(new FormHttpMessageConverter());
		MappingJacksonHttpMessageConverter jacksonConverter = new MappingJacksonHttpMessageConverter();
		List<MediaType> list = new ArrayList<MediaType>();
		list.addAll(jacksonConverter.getSupportedMediaTypes());
		list.add(MediaType.TEXT_PLAIN);
		jacksonConverter.setSupportedMediaTypes(list);
		converters.add(jacksonConverter);
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

}
