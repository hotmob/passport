/**
 * 
 */
package com.ammob.passport.social.weibo.api.v1;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.web.client.RestTemplate;

/**
 * @author iday
 *
 */
public class AbstractWeiboOperations extends AbstractOAuth1ApiBinding {

	protected static final String API_URL = "http://api.t.sina.com.cn/";
	
	private final boolean isAuthorized;
	protected final RestTemplate restTemplate;
	protected final WeiboTemplate api;

	public AbstractWeiboOperations(WeiboTemplate api, RestTemplate restTemplate, boolean isAuthorized) {
		this.api = api;
		this.restTemplate = restTemplate;
		this.isAuthorized = isAuthorized;
	}
	
	protected void requireAuthorization() {
		if (!isAuthorized) {
			throw new MissingAuthorizationException();
		}
	}

}
