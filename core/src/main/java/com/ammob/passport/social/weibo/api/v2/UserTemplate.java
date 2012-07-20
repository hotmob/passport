/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.weibo.api.UserOperations;
import com.ammob.passport.social.weibo.api.Weibo;
import com.ammob.passport.social.weibo.api.WeiboProfile;

/**
 * @author iday
 * 
 */
public class UserTemplate extends AbstractWeiboOperations implements
		UserOperations {

	public UserTemplate(Weibo api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	@Override
	public WeiboProfile getUserProfile() {
		requireAuthorization();
		JsonNode json = this.restTemplate.getForObject(
				buildUrl("account/get_uid.json"), JsonNode.class);
		return this.restTemplate.getForObject(
				buildUrl("users/show.json", "uid", json.get("uid").asText()),
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile getUserProfile(long uid) {
		requireAuthorization();
		return this.restTemplate.getForObject(
				buildUrl("users/show.json", "uid", String.valueOf(uid)),
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile getUserProfile(String screenName) {
		requireAuthorization();
		return this.restTemplate.getForObject(
				buildUrl("users/show.json", "screen_name", screenName),
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile createFriendship(long id) {
		requireAuthorization();
		MultiValueMap<String, Object> userParams = new LinkedMultiValueMap<String, Object>();
		userParams.add("uid", String.valueOf(id));
		return this.restTemplate.postForObject(
				buildUrl("friendships/create.json"), userParams,
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile createFriendship(String screenName) {
		requireAuthorization();
		MultiValueMap<String, Object> userParams = new LinkedMultiValueMap<String, Object>();
		userParams.add("screen_name", screenName);
		return this.restTemplate.postForObject(
				buildUrl("friendships/create.json"), userParams,
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile destroyFriendship(long id) {
		requireAuthorization();
		MultiValueMap<String, Object> userParams = new LinkedMultiValueMap<String, Object>();
		userParams.add("uid", String.valueOf(id));
		return this.restTemplate.postForObject(
				buildUrl("friendships/destroy.json"), userParams,
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile destroyFriendship(String screenName) {
		requireAuthorization();
		MultiValueMap<String, Object> userParams = new LinkedMultiValueMap<String, Object>();
		userParams.add("screen_name", screenName);
		return this.restTemplate.postForObject(
				buildUrl("friendships/destroy.json"), userParams,
				WeiboProfile.class);
	}

	@Override
	public WeiboProfile notInterestedUser(long id) {
		requireAuthorization();
		MultiValueMap<String, Object> userParams = new LinkedMultiValueMap<String, Object>();
		userParams.add("uid", id);
		return this.restTemplate.postForObject(
				buildUrl("suggestions/users/not_interested.json"), userParams,
				WeiboProfile.class);
	}

	@Override
	public List<WeiboProfile> getFriends(long id, int count, int cursor) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("count", String.valueOf(count));
		userParams.add("cursor", String.valueOf(cursor));
		return this.restTemplate.getForObject(
				buildUrl("friendships/friends.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFriends(String screenName, int count,
			int cursor) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("screen_name", screenName);
		userParams.add("count", String.valueOf(count));
		userParams.add("cursor", String.valueOf(cursor));
		return this.restTemplate.getForObject(
				buildUrl("friendships/friends.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFriends(long id) {
		return getFriends(id, 50, 0);
	}

	@Override
	public List<WeiboProfile> getFriends(String screenName) {
		return getFriends(screenName, 50, 0);
	}

	@Override
	public List<WeiboProfile> getFriendsInCommon(long id, long sid, int count,
			int page) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("uid", String.valueOf(sid));
		userParams.add("count", String.valueOf(count));
		userParams.add("page", String.valueOf(page));
		return this.restTemplate.getForObject(
				buildUrl("friendships/friends/in_common.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFriendsInCommon(long id, int count, int page) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("count", String.valueOf(count));
		userParams.add("page", String.valueOf(page));
		return this.restTemplate.getForObject(
				buildUrl("friendships/friends/in_common.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFriendsInCommon(long id, int page) {
		return getFriendsInCommon(id, 50, page);
	}

	@Override
	public List<WeiboProfile> getFriendsInCommon(long id) {
		return getFriendsInCommon(id, 1);
	}

	@Override
	public List<WeiboProfile> getFriendsBilateral(long id, int count, int page,
			int sort) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("count", String.valueOf(count));
		userParams.add("page", String.valueOf(page));
		userParams.add("sort", String.valueOf(sort));
		return this.restTemplate.getForObject(
				buildUrl("friendships/friends/bilateral.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFriendsBilateral(long id, int count, int page) {
		return getFriendsBilateral(id, count, page, 0);
	}

	@Override
	public List<WeiboProfile> getFriendsBilateral(long id, int page) {
		return getFriendsBilateral(id, 50, page);
	}

	@Override
	public List<WeiboProfile> getFriendsBilateral(long id) {
		return getFriendsBilateral(id, 1);
	}

	@Override
	public List<WeiboProfile> getFollowers(long id, int count, int cursor) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("count", String.valueOf(count));
		userParams.add("cursor", String.valueOf(cursor));
		return this.restTemplate.getForObject(
				buildUrl("friendships/followers.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFollowers(String screenName, int count,
			int cursor) {
		requireAuthorization();
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("screen_name", screenName);
		userParams.add("count", String.valueOf(count));
		userParams.add("cursor", String.valueOf(cursor));
		return this.restTemplate.getForObject(
				buildUrl("friendships/followers.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getFollowers(long id) {
		return getFollowers(id, 50, 0);
	}

	@Override
	public List<WeiboProfile> getFollowers(String screenName) {
		return getFollowers(screenName, 50, 0);
	}

	@Override
	public List<WeiboProfile> getFollowersActive(long id, int count) {
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("count", String.valueOf(count));
		return this.restTemplate.getForObject(
				buildUrl("friendships/followers/active.json", userParams),
				UserArrayList.class);
	}

	@Override
	public List<WeiboProfile> getFriendsChainFollowers(long id, int count,
			int page) {
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("uid", String.valueOf(id));
		userParams.add("count", String.valueOf(count));
		userParams.add("page", String.valueOf(page));
		return this.restTemplate
				.getForObject(
						buildUrl("friendships/friends_chain/followers.json",
								userParams), UserArrayList.class);
	}

	@Override
	public List<WeiboProfile> getFriendsChainFollowers(long id) {
		return getFriendsChainFollowers(id, 50, 1);
	}

	@Override
	public List<WeiboProfile> getHotUserSuggestions(String category) {
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("category", category);
		return this.restTemplate.getForObject(
				buildUrl("suggestions/users/hot.json", userParams),
				UserArrayList.class);
	}

	@Override
	public List<WeiboProfile> getHotUserSuggestions() {
		return getHotUserSuggestions("default");
	}

	@Override
	public List<WeiboProfile> getUserSuggestionsByStatus(String content,
			int count) {
		MultiValueMap<String, String> userParams = new LinkedMultiValueMap<String, String>();
		userParams.add("content", content);
		userParams.add("num", String.valueOf(count));
		return this.restTemplate.getForObject(
				buildUrl("suggestions/users/by_status.json", userParams),
				UserList.class).getUsers();
	}

	@Override
	public List<WeiboProfile> getUserSuggestionsByStatus(String content) {
		return getUserSuggestionsByStatus(content, 10);
	}

	private static class UserArrayList extends ArrayList<WeiboProfile> {

		/**
			 * 
			 */
		private static final long serialVersionUID = 1070420145284073706L;

	}

}
