/**
 * 
 */
package com.ammob.passport.social.txwb.api.oauth1;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.txwb.api.Txwb;

/**
 * @author iday
 * 
 */
public class AbstractTxwbOperations extends AbstractOAuth1ApiBinding {

	protected static final String API_URL = "http://open.t.qq.com/api/";

	private final boolean isAuthorized;
	protected final RestTemplate restTemplate;
	protected final Txwb api;

	public AbstractTxwbOperations(Txwb api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super();
		this.api = api;
		this.restTemplate = restTemplate;
		this.isAuthorized = isAuthorized;
	}

	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException();
		}
	}

	protected URI buildUrl(String url) {
		return buildUrl(url, new LinkedMultiValueMap<String, String>());
	}

	protected URI buildUrl(String url, MultiValueMap<String, String> parameters, HttpMethod method) {
		return URIBuilder.fromUri(API_URL + url).queryParams(api.commonOAuthParameters(method, API_URL + url, parameters))
				.build();
	}
	
	protected URI buildUrl(String url, MultiValueMap<String, String> parameters) {
		return buildUrl(url, parameters, HttpMethod.GET);
	}

	protected URI buildUrl(String url, String name, String value) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add(name, value);
		return buildUrl(url, parameters);
	}

}
