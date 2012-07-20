/**
 * 
 */
package com.ammob.passport.social.weibo.connect.v2;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.ammob.passport.social.weibo.api.Weibo;
import com.ammob.passport.social.weibo.connect.WeiboAdapter;

/**
 * @author iday
 * 
 */
public class WeiboConnectionFactory extends OAuth2ConnectionFactory<Weibo> {

	public WeiboConnectionFactory(String clientId, String clientSecret) {
		super("weibo", new WeiboServiceProvider(clientId, clientSecret),
				new WeiboAdapter());
	}

}
