/**
 * 
 */
package com.ammob.passport.social.weibo.connect.v2;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.support.HttpRequestDecorator;


/**
 * @author iday
 *
 */
public class WeiboRequestInterceptor implements ClientHttpRequestInterceptor {

	private final String accessToken;

	/**
	 * @param accessToken
	 */
	public WeiboRequestInterceptor(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
			ClientHttpRequestExecution execution) throws IOException {
		HttpRequest protectedResourceRequest = new HttpRequestDecorator(request);
		protectedResourceRequest.getHeaders().set("Authorization", "OAuth2 " + accessToken);
		return execution.execute(protectedResourceRequest, body);
	}

}
