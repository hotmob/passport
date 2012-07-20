/**
 * 
 */
package com.ammob.passport.social.txwb.api.oauth1;

import java.util.List;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.txwb.api.Txwb;
import com.ammob.passport.social.txwb.api.TxwbProfile;
import com.ammob.passport.social.txwb.api.TxwbProfileList;
import com.ammob.passport.social.txwb.api.UserOperations;

/**
 * @author iday
 * 
 */
public class UserTemplate extends AbstractTxwbOperations implements
		UserOperations {

	public UserTemplate(Txwb api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	@Override
	public TxwbProfile getUserProfile() {
		isAuthorized();
		return restTemplate.getForObject(buildUrl("user/info"),
				TxwbProfile.class);
	}

	@Override
	public TxwbProfile getUserProfile(String screenName) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", screenName);
		return restTemplate.getForObject(buildUrl("user/other_info", params),
				TxwbProfile.class);
	}

	@Override
	public void update(String nick, int sex, int year, int month, int day,
			String countrycode, String provincecode, String citycode,
			String introduction) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("nick", nick);
		params.add("sex", String.valueOf(sex));
		params.add("year", String.valueOf(year));
		params.add("month", String.valueOf(month));
		params.add("day", String.valueOf(day));
		params.add("countrycode", countrycode);
		params.add("provincecode", provincecode);
		params.add("citycode", citycode);
		params.add("introduction", introduction);
		restTemplate.postForLocation(buildUrl("user/update"), params);
	}
	
	@Override
	public void addFollowing(String...names) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", StringUtils.arrayToCommaDelimitedString(names));
		restTemplate.postForLocation(buildUrl("friends/add"), params);
	}
	
	@Override
	public void delFollowing(String name) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		restTemplate.postForLocation(buildUrl("friends/del"), params);
	}
	
	@Override
	public void addSpecial(String name) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		restTemplate.postForLocation(buildUrl("friends/addspecial"), params);
	}
	
	@Override
	public void delSpecial(String name) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		restTemplate.postForLocation(buildUrl("friends/delspecial"), params);
	}
	
	@Override
	public void addBlackList(String name) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		restTemplate.postForLocation(buildUrl("friends/addblacklist"), params);
	}
	
	@Override
	public void delBlackList(String name) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		restTemplate.postForLocation(buildUrl("friends/delblacklist"), params);
	}

	@Override
	public List<TxwbProfile> getFollowers(int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/fanslist", params),
				TxwbProfileList.class).getInfo();
	}

	@Override
	public List<TxwbProfile> getFollowers(String name, int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/user_fanslist", params),
				TxwbProfileList.class).getInfo();
	}
	
	@Override
	public List<TxwbProfile> getFollowerNames(int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/fanslist_name", params),
				TxwbProfileList.class).getInfo();
	}
	
	@Override
	public List<TxwbProfile> getFollowing(int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/idollist", params),
				TxwbProfileList.class).getInfo();
	}

	@Override
	public List<TxwbProfile> getFollowing(String name, int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/user_idollist", params),
				TxwbProfileList.class).getInfo();
	}
	
	@Override
	public List<TxwbProfile> getFollowingNames(int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/idollist_name", params),
				TxwbProfileList.class).getInfo();
	}
	
	@Override
	public List<TxwbProfile> getBlackList(int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/blacklist", params),
				TxwbProfileList.class).getInfo();
	}
	
	@Override
	public List<TxwbProfile> getSpecialList(int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/speciallist", params),
				TxwbProfileList.class).getInfo();
	}
	
	@Override
	public List<TxwbProfile> getSpecialList(String name, int reqnum, int startindex) {
		isAuthorized();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name", name);
		params.add("reqnum", String.valueOf(reqnum));
		params.add("startindex", String.valueOf(startindex));
		return restTemplate.getForObject(buildUrl("friends/user_speciallist", params),
				TxwbProfileList.class).getInfo();
	}

}
