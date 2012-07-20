/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author iday
 * 
 */
public class AbstractRenrenOperations extends AbstractOAuth2ApiBinding {

	protected static final String API_URL = "http://api.renren.com/restserver.do";

	private final boolean isAuthorized;
	protected final RestTemplate restTemplate;
	protected final Renren api;

	public AbstractRenrenOperations(Renren api, RestTemplate restTemplate,
			boolean isAuthorized) {
		this.api = api;
		this.restTemplate = restTemplate;
		this.isAuthorized = isAuthorized;
	}

	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException();
		}
	}
	
	protected <T extends Object>T post(MultiValueMap<String, String> parameters, Class<T> responseType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		String accessToken = api.getAccessToken();
		params.add("access_token", accessToken);
		params.add("v", "1.0");
		params.add("format", "JSON");
		params.putAll(parameters);
		params.add("sig", getSignature(params));
		return restTemplate.postForObject(API_URL, params, responseType);
	}
	
	protected <T extends Object>T post(String name, String value, Class<T> responseType) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		String accessToken = api.getAccessToken();
		params.add("access_token", accessToken);
		params.add("v", "1.0");
		params.add("format", "JSON");
		params.add(name, value);
		params.add("sig", getSignature(params));
		return restTemplate.postForObject(API_URL, params, responseType);
	}

	/**
	 * @param params
	 * @return
	 */
	protected String getSignature(MultiValueMap<String, String> params) {
		String sig;
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuilder sb = new StringBuilder();
		for (String key : keys) {
			sb.append(key).append("=").append(params.getFirst(key));
		}
		sb.append(api.getClientSecret());
		sig = DigestUtils.md5Hex(sb.toString());
		return sig;
	}

}
