/**
 * 
 */
package com.ammob.passport.social.renren.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.ammob.passport.social.renren.api.Renren;
import com.ammob.passport.social.renren.api.RenrenProfile;

/**
 * @author iday
 *
 */
public class RenrenAdapter implements ApiAdapter<Renren> {

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ApiAdapter#fetchUserProfile(java.lang.Object)
	 */
	@Override
	public UserProfile fetchUserProfile(Renren Renren) {
		RenrenProfile profile = Renren.getUserOperations().getUserProfile();
		return new UserProfileBuilder().setName(profile.getName())
				.setUsername(profile.getName()).build();
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ApiAdapter#setConnectionValues(java.lang.Object, org.springframework.social.connect.ConnectionValues)
	 */
	@Override
	public void setConnectionValues(Renren renren, ConnectionValues values) {
		RenrenProfile profile = renren.getUserOperations().getUserProfile();
		values.setProviderUserId(String.valueOf(profile.getId())); //需要处理
		values.setDisplayName(profile.getName());
		values.setImageUrl(profile.getHeadurl());
		values.setProfileUrl("http://www.renren.com/profile.do?id=" + profile.getId());
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ApiAdapter#test(java.lang.Object)
	 */
	@Override
	public boolean test(Renren arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.ApiAdapter#updateStatus(java.lang.Object, java.lang.String)
	 */
	@Override
	public void updateStatus(Renren arg0, String arg1) {
		// TODO Auto-generated method stub

	}

}
