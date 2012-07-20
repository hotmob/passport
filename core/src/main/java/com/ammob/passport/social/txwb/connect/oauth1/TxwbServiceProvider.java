/**
 * 
 */
package com.ammob.passport.social.txwb.connect.oauth1;

import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;

import com.ammob.passport.social.txwb.api.Txwb;
import com.ammob.passport.social.txwb.api.oauth1.TxwbTemplate;

/**
 * @author iday
 * 
 */
public class TxwbServiceProvider extends AbstractOAuth1ServiceProvider<Txwb> {

	public TxwbServiceProvider(String consumerKey, String consumerSecret) {
		super(consumerKey, consumerSecret, new TxwbOauth1Template(consumerKey,
				consumerSecret, "https://open.t.qq.com/cgi-bin/request_token",
				"https://open.t.qq.com/cgi-bin/authorize",
				"https://open.t.qq.com/cgi-bin/access_token"));
	}

	@Override
	public Txwb getApi(String accessToken, String secret) {
		return new TxwbTemplate(getConsumerKey(), getConsumerSecret(),
				accessToken, secret);
	}

}
