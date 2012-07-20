/**
 * 
 */
package com.ammob.passport.social.renren.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.renren.api.AbstractRenrenOperations;
import com.ammob.passport.social.renren.api.Renren;
import com.ammob.passport.social.renren.api.RenrenProfile;
import com.ammob.passport.social.renren.api.UserOperations;


/**
 * @author iday
 * 
 */
public class UserTemplate extends AbstractRenrenOperations implements UserOperations {

	public UserTemplate(Renren api, RestTemplate restTemplate,
			ObjectMapper objectMapper, boolean isAuthorizedForUser) {
		super(api, restTemplate, isAuthorizedForUser);
	}

	@Override
	public List<RenrenProfile> getUserProfiles(String... ids) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("method", "users.getInfo");
		if (ids.length > 0) {
			params.add("uids", StringUtils.join(ids));
		}
		return post(params, RenrenProfileList.class);
	}

	@Override
	public RenrenProfile getUserProfile() {
		return getUserProfiles().get(0);
	}
	
	
	
	private static class RenrenProfileList extends ArrayList<RenrenProfile> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7316943717530657260L;
		
	}

}
