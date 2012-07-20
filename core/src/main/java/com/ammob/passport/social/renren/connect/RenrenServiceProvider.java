/**
 * 
 */
package com.ammob.passport.social.renren.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

import com.ammob.passport.social.renren.api.Renren;

/**
 * @author iday
 * 
 */
public class RenrenServiceProvider extends
		AbstractOAuth2ServiceProvider<Renren> {
	private final String clientSecret;

	/**
	 * @param clientId
	 * @param clientSecret
	 */
	public RenrenServiceProvider(String clientId, String clientSecret) {
		super(new OAuth2Template(clientId, clientSecret,
				"https://graph.renren.com/oauth/authorize",
				"https://graph.renren.com/oauth/token"));
		this.clientSecret = clientSecret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.social.oauth2.OAuth2ServiceProvider#getApi(java.lang
	 * .String)
	 */
	@Override
	public Renren getApi(String accessToken) {
		return new Renren(accessToken, clientSecret);
	}

}
