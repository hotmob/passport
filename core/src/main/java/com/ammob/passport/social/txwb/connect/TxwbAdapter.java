/**
 * 
 */
package com.ammob.passport.social.txwb.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.ammob.passport.social.txwb.api.Txwb;
import com.ammob.passport.social.txwb.api.TxwbProfile;

/**
 * @author iday
 * 
 */
public class TxwbAdapter implements ApiAdapter<Txwb> {

	@Override
	public UserProfile fetchUserProfile(Txwb txwb) {
		TxwbProfile profile = txwb.userOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getName())
				.setUsername(profile.getNick()).build();
	}

	@Override
	public void setConnectionValues(Txwb txwb, ConnectionValues values) {
		TxwbProfile profile = txwb.userOperations().getUserProfile();
		values.setProviderUserId(profile.getOpenId());
		values.setDisplayName(profile.getNick());
		values.setImageUrl(profile.getHead());
		values.setProfileUrl("http://t.qq.com/" + profile.getName());
	}

	@Override
	public boolean test(Txwb txwb) {
		try {
			txwb.userOperations().getUserProfile();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void updateStatus(Txwb txwb, String message) {
		// qqt.timelineOperations().updateStatus(message);
	}

}
