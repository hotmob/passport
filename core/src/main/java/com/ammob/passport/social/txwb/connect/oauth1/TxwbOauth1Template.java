/**
 * 
 */
package com.ammob.passport.social.txwb.connect.oauth1;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuth1Version;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author iday
 * 
 */
public class TxwbOauth1Template extends OAuth1Template {
	private final TxwbOauth1Support oauth1Support;
	private final RestTemplate restTemplate;
	private final String requestTokenUrl;
	private final String accessTokenUrl;
	private final String consumerKey;
	private final String consumerSecret;

	public TxwbOauth1Template(String consumerKey, String consumerSecret,
			String requestTokenUrl, String authorizeUrl, String accessTokenUrl) {
		super(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl,
				accessTokenUrl);
		this.restTemplate = createRestTemplate();
		this.requestTokenUrl = requestTokenUrl;
		this.accessTokenUrl = accessTokenUrl;
		this.oauth1Support = new TxwbOauth1Support();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}

	private RestTemplate createRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(
				ClientHttpRequestFactorySelector.getRequestFactory());
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>(
				1);
		converters.add(new FormHttpMessageConverter() {
			public boolean canRead(Class<?> clazz, MediaType mediaType) {
				// always read MultiValueMaps as x-www-url-formencoded even if
				// contentType not set properly by provider
				return MultiValueMap.class.isAssignableFrom(clazz);
			}
		});
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.social.oauth1.OAuth1Template#fetchRequestToken(java
	 * .lang.String, org.springframework.util.MultiValueMap)
	 */
	@Override
	public OAuthToken fetchRequestToken(String callbackUrl,
			MultiValueMap<String, String> additionalParameters) {
		additionalParameters = new LinkedMultiValueMap<String, String>(1);
		if (this.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
			additionalParameters.add("oauth_callback", callbackUrl);
		}
		return exchangeForToken(this.requestTokenUrl, additionalParameters, "");
	}

	/**
	 * @param additionalParameters
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private OAuthToken exchangeForToken(String otokenUrl,
			MultiValueMap<String, String> additionalParameters,
			String tokenSecret) {
		URI tokenUrl = URIBuilder
				.fromUri(otokenUrl)
				.queryParams(
						oauth1Support.commonOAuthParameters(consumerKey,
								HttpMethod.GET, otokenUrl,
								additionalParameters, consumerSecret,
								tokenSecret)).build();
		ResponseEntity<MultiValueMap> response = restTemplate.exchange(
				tokenUrl, HttpMethod.GET, null, MultiValueMap.class);
		MultiValueMap<String, String> body = response.getBody();
		return createOAuthToken(body.getFirst("oauth_token"),
				body.getFirst("oauth_token_secret"), body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.social.oauth1.OAuth1Template#exchangeForAccessToken
	 * (org.springframework.social.oauth1.AuthorizedRequestToken,
	 * org.springframework.util.MultiValueMap)
	 */
	@Override
	public OAuthToken exchangeForAccessToken(
			AuthorizedRequestToken requestToken,
			MultiValueMap<String, String> additionalParameters) {
		additionalParameters = new LinkedMultiValueMap<String, String>(1);
		additionalParameters.add("oauth_token", requestToken.getValue());
		if (this.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
			additionalParameters.add("oauth_verifier",
					requestToken.getVerifier());
		}
		return exchangeForToken(this.accessTokenUrl, additionalParameters,
				requestToken.getSecret());
	}

}
