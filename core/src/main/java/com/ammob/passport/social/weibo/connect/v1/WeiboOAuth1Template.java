/**
 * 
 */
package com.ammob.passport.social.weibo.connect.v1;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuth1Version;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.util.MultiValueMap;

/**
 * @author iday
 *
 */
public class WeiboOAuth1Template extends OAuth1Template {
	private String callbackUrl;

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param requestTokenUrl
	 * @param authorizeUrl
	 * @param accessTokenUrl
	 * @param version
	 */
	public WeiboOAuth1Template(String consumerKey, String consumerSecret,
			String requestTokenUrl, String authorizeUrl, String accessTokenUrl,
			OAuth1Version version) {
		super(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl,
				accessTokenUrl, version);
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param requestTokenUrl
	 * @param authorizeUrl
	 * @param authenticateUrl
	 * @param accessTokenUrl
	 * @param version
	 */
	public WeiboOAuth1Template(String consumerKey, String consumerSecret,
			String requestTokenUrl, String authorizeUrl,
			String authenticateUrl, String accessTokenUrl, OAuth1Version version) {
		super(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl,
				authenticateUrl, accessTokenUrl, version);
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param requestTokenUrl
	 * @param authorizeUrl
	 * @param authenticateUrl
	 * @param accessTokenUrl
	 */
	public WeiboOAuth1Template(String consumerKey, String consumerSecret,
			String requestTokenUrl, String authorizeUrl,
			String authenticateUrl, String accessTokenUrl) {
		super(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl,
				authenticateUrl, accessTokenUrl);
	}

	/**
	 * @param consumerKey
	 * @param consumerSecret
	 * @param requestTokenUrl
	 * @param authorizeUrl
	 * @param accessTokenUrl
	 */
	public WeiboOAuth1Template(String consumerKey, String consumerSecret,
			String requestTokenUrl, String authorizeUrl, String accessTokenUrl) {
		super(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl,
				accessTokenUrl);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.oauth1.OAuth1Template#fetchRequestToken(java.lang.String, org.springframework.util.MultiValueMap)
	 */
	@Override
	public OAuthToken fetchRequestToken(String callbackUrl,
			MultiValueMap<String, String> additionalParameters) {
		this.callbackUrl = callbackUrl;
		return super.fetchRequestToken(callbackUrl, additionalParameters);
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.oauth1.OAuth1Template#buildAuthorizeUrl(java.lang.String, org.springframework.social.oauth1.OAuth1Parameters)
	 */
	@Override
	public String buildAuthorizeUrl(String requestToken,
			OAuth1Parameters parameters) {
		StringBuilder authUrl = new StringBuilder(super.buildAuthorizeUrl(requestToken, parameters));
		try {
			authUrl.append('&').append("oauth_callback").append("=").append(URLEncoder.encode(this.callbackUrl, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		return authUrl.toString();
	}

}
