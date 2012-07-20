/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import org.springframework.http.HttpMethod;
import org.springframework.social.ApiBinding;
import org.springframework.util.MultiValueMap;

/**
 * @author iday
 * 
 */
public interface Txwb extends ApiBinding {

	public abstract UserOperations userOperations();

	public abstract MultiValueMap<String, String> commonOAuthParameters(
			HttpMethod method, String targetUrl,
			MultiValueMap<String, String> additionalParameters);

}
