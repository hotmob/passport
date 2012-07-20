/**
 * 
 */
package com.ammob.passport.social.renren.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

import com.ammob.passport.social.renren.api.Renren;

/**
 * @author iday
 * 
 */
public class RenrenConnectionFactory extends OAuth2ConnectionFactory<Renren> {

	public RenrenConnectionFactory(String clientId, String clientSecret) {
		super("renren", new RenrenServiceProvider(clientId, clientSecret),
				new RenrenAdapter());
	}

}
