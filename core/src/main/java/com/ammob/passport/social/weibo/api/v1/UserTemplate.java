/**
 * 
 */
package com.ammob.passport.social.weibo.api.v1;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.weibo.api.UserOperations;
import com.ammob.passport.social.weibo.api.WeiboProfile;

/**
 * @author iday
 * 
 */
public class UserTemplate extends AbstractWeiboOperations implements UserOperations {

	public UserTemplate(WeiboTemplate api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	public WeiboProfile getUserProfile() {
		String api = API_URL + "account/verify_credentials.json";
		return restTemplate.getForObject(api, WeiboProfile.class);
	}

	public List<WeiboProfile> getFriendsChainFollowers(long id) {
		return null;
	}

	public List<WeiboProfile> getFriendsChainFollowers(long id, int count,
			int page) {
		return null;
	}

	public List<WeiboProfile> getFollowersActive(long id, int count) {
		return null;
	}

	public List<WeiboProfile> getFollowers(String screenName) {
		return null;
	}

	public List<WeiboProfile> getFollowers(long id) {
		return null;
	}

	public List<WeiboProfile> getFollowers(String screenName, int count, int cursor) {
		return null;
	}

	public List<WeiboProfile> getFollowers(long id, int count, int cursor) {
		return null;
	}

	public List<WeiboProfile> getFriendsBilateral(long id) {
		return null;
	}

	public List<WeiboProfile> getFriendsBilateral(long id, int page) {
		return null;
	}

	public List<WeiboProfile> getFriendsBilateral(long id, int count, int page) {
		return null;
	}

	public List<WeiboProfile> getFriendsBilateral(long id, int count, int page,
			int sort) {
		return null;
	}

	public List<WeiboProfile> getFriendsInCommon(long id) {
		return null;
	}

	public List<WeiboProfile> getFriendsInCommon(long id, int page) {
		return null;
	}

	public List<WeiboProfile> getFriendsInCommon(long id, int count, int page) {
		return null;
	}

	public List<WeiboProfile> getFriendsInCommon(long id, long sid, int count,
			int page) {
		return null;
	}

	public List<WeiboProfile> getFriends(String screenName) {
		return null;
	}

	public List<WeiboProfile> getFriends(long id) {
		return null;
	}

	public List<WeiboProfile> getFriends(String screenName, int count, int cursor) {
		return null;
	}

	public List<WeiboProfile> getFriends(long id, int count, int cursor) {
		return null;
	}

	public WeiboProfile destroyFriendship(String screenName) {
		return null;
	}

	public WeiboProfile destroyFriendship(long id) {
		return null;
	}

	public WeiboProfile createFriendship(String screenName) {
		return null;
	}

	public WeiboProfile createFriendship(long id) {
		return null;
	}

	public WeiboProfile getUserProfile(String screenName) {
		return null;
	}

	public WeiboProfile getUserProfile(long uid) {
		return null;
	}

	public List<WeiboProfile> getUserSuggestionsByStatus(String content) {
		return null;
	}

	public List<WeiboProfile> getUserSuggestionsByStatus(String content, int count) {
		return null;
	}

	public List<WeiboProfile> getHotUserSuggestions() {
		return null;
	}

	public List<WeiboProfile> getHotUserSuggestions(String category) {
		return null;
	}

	public WeiboProfile notInterestedUser(long id) {
		return null;
	}

}
