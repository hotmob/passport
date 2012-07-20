/**
 * 
 */
package com.ammob.passport.social.renren.api.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.module.SimpleModule;

import com.ammob.passport.social.renren.api.Place;
import com.ammob.passport.social.renren.api.RenrenProfile;
import com.ammob.passport.social.renren.api.StatusPost;

/**
 * @author iday
 *
 */
public class RenrenModule extends SimpleModule {

	public RenrenModule() {
		super("RenrenModule", new Version(1, 0, 0, null));
	}

	/* (non-Javadoc)
	 * @see org.codehaus.jackson.map.module.SimpleModule#setupModule(org.codehaus.jackson.map.Module.SetupContext)
	 */
	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(RenrenProfile.class, RenrenProfileMixin.class);
		context.setMixInAnnotations(StatusPost.class, StatusPostMixin.class);
		context.setMixInAnnotations(Place.class, PlaceMixin.class);
	}

}
