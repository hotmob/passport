/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.util.List;



/**
 * @author iday
 * 
 */
public interface UserOperations {

	public abstract RenrenProfile getUserProfile();

	public abstract List<RenrenProfile> getUserProfiles(String... ids);

}
