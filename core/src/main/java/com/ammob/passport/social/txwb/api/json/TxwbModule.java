/**
 * 
 */
package com.ammob.passport.social.txwb.api.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import com.ammob.passport.social.txwb.api.Tweet;
import com.ammob.passport.social.txwb.api.TxwbProfile;

/**
 * @author iday
 * 
 */
public class TxwbModule extends SimpleModule {

	public TxwbModule() {
		super("qqt", new Version(1, 0, 0, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.codehaus.jackson.map.module.SimpleModule#setupModule(org.codehaus
	 * .jackson.map.Module.SetupContext)
	 */
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(TxwbProfile.class, TxwbProfileMixin.class);
		context.setMixInAnnotations(Tweet.class, TweetMixin.class);
	}

}
