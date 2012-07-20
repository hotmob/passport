/**
 * 
 */
package com.ammob.passport.social.txwb.connect.oauth1;

import org.springframework.social.connect.support.OAuth1ConnectionFactory;

import com.ammob.passport.social.txwb.api.Txwb;
import com.ammob.passport.social.txwb.connect.TxwbAdapter;

/**
 * @author iday
 * 
 */
public class TxwbConnectionFactory extends OAuth1ConnectionFactory<Txwb> {

	public TxwbConnectionFactory(String consumerKey, String consumerSecret) {
		super("txwb", new TxwbServiceProvider(consumerKey, consumerSecret),
				new TxwbAdapter());
	}

}
