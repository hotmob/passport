/**
 * 
 */
package com.ammob.passport.social.weibo.connect;

import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.ammob.passport.social.weibo.api.Weibo;
import com.ammob.passport.social.weibo.api.WeiboProfile;

/**
 * @author iday
 * 
 */
public class WeiboAdapter implements ApiAdapter<Weibo> {

	@Override
	public UserProfile fetchUserProfile(Weibo weibo) {
		WeiboProfile profile = weibo.userOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getName())
				.setUsername(profile.getScreenName()).build();
	}

	@Override
	public void setConnectionValues(Weibo weibo, ConnectionValues values) {
		WeiboProfile profile = weibo.userOperations().getUserProfile();
		values.setProviderUserId(String.valueOf(profile.getId()));
		values.setDisplayName(profile.getScreenName());
		values.setImageUrl(profile.getAvatarLarge());
		values.setProfileUrl(StringUtils.defaultString(profile.getDomain(), String.valueOf(profile.getId())));
	}

	@Override
	public boolean test(Weibo weibo) {
		try {
			weibo.userOperations().getUserProfile();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void updateStatus(Weibo weibo, String message) {
		weibo.timelineOperations().updateStatus(message);
	}

}
