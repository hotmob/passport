/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.weibo.api.Geo;
import com.ammob.passport.social.weibo.api.TimelineOperations;
import com.ammob.passport.social.weibo.api.Tweet;
import com.ammob.passport.social.weibo.api.Weibo;

/**
 * @author iday
 * 
 */
public class TimelineTemplate extends AbstractWeiboOperations implements
		TimelineOperations {

	public TimelineTemplate(Weibo api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.StatusesOperations#updateStatus(java
	 * .lang.String)
	 */
	@Override
	public Tweet updateStatus(String message) {
		requireAuthorization();
		return updateStatus(message, new Geo());
	}

	@Override
	public Tweet updateStatus(String message, Geo geo) {
		requireAuthorization();
		MultiValueMap<String, Object> statusParams = new LinkedMultiValueMap<String, Object>();
		statusParams.add("status", message);
		statusParams.putAll(geo.toParameterMap());
		return restTemplate.postForObject(buildUrl("statuses/update.json"),
				statusParams, Tweet.class);
	}

	@Override
	public Tweet updateStatus(String message, Resource image, Geo geo) {
		requireAuthorization();
		MultiValueMap<String, Object> statusParams = new LinkedMultiValueMap<String, Object>();
		statusParams.add("status", message);
		statusParams.add("pic", image);
		statusParams.putAll(geo.toParameterMap());
		return restTemplate.postForObject(buildUrl("statuses/upload.json"),
				statusParams, Tweet.class);
	}

	@Override
	public Tweet updateStatus(String message, Resource image) {
		requireAuthorization();
		return updateStatus(message, image, new Geo());
	}

	@Override
	public Tweet getStatus(long id) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("id", String.valueOf(id));
		return this.restTemplate.getForObject(
				buildUrl("statuses/show.json", statusParams), Tweet.class);
	}

	@Override
	public Tweet destroyStatus(long id) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("id", String.valueOf(id));
		return this.restTemplate.getForObject(
				buildUrl("statuses/destroy.json", statusParams), Tweet.class);
	}

	@Override
	public Tweet repostStatus(long id, String message, int comment) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("id", String.valueOf(id));
		if (StringUtils.isNotEmpty(message)) {
			statusParams.add("status", message);
		}
		statusParams.add("is_comment", String.valueOf(comment));
		return this.restTemplate.getForObject(
				buildUrl("statuses/repost.json", statusParams), Tweet.class);
	}

	@Override
	public Tweet repostStatus(long id) {
		return repostStatus(id, "", 0);
	}

	@Override
	public List<Tweet> getPublicTimeline(int count, int page, int baseApp) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("count", String.valueOf(count)); // 单页返回的记录条数，默认为50。
		statusParams.add("page", String.valueOf(page)); // 返回结果的页码，默认为1。
		statusParams.add("base_app", String.valueOf(baseApp)); // 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
		return this.restTemplate.getForObject(
				buildUrl("statuses/public_timeline.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getPublicTimeline() {
		return getPublicTimeline(50, 1, 0);
	}

	@Override
	public List<Tweet> getFriendsTimeline(long sinceId, long maxId, int count,
			int page, int baseApp, int feature) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("since_id", String.valueOf(sinceId)); // 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博），默认为0。
		statusParams.add("max_id", String.valueOf(maxId)); // 若指定此参数，则返回ID小于或等于max_id的微博，默认为0。
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		statusParams.add("base_app", String.valueOf(baseApp));
		statusParams.add("feature", String.valueOf(feature)); // 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
		return this.restTemplate.getForObject(
				buildUrl("statuses/friends_timeline.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getFriendsTimeline(int count, int page, int feature) {
		return this.getFriendsTimeline(0, 0, count, page, 0, feature);
	}

	@Override
	public List<Tweet> getFriendsTimeline(int feature) {
		return this.getFriendsTimeline(0, 0, feature);
	}

	@Override
	public List<Tweet> getFriendsTimeline() {
		return this.getFriendsTimeline(0);
	}

	@Override
	public List<Tweet> getHomeTimeline(long sinceId, long maxId, int count,
			int page, int baseApp, int feature) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("since_id", String.valueOf(sinceId));
		statusParams.add("max_id", String.valueOf(maxId));
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		statusParams.add("base_app", String.valueOf(baseApp));
		statusParams.add("feature", String.valueOf(feature));
		return this.restTemplate.getForObject(
				buildUrl("statuses/home_timeline.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getHomeTimeline(int count, int page, int feature) {
		return this.getHomeTimeline(0, 0, count, page, 0, feature);
	}

	@Override
	public List<Tweet> getHomeTimeline(int feature) {
		return this.getHomeTimeline(50, 1, feature);
	}

	@Override
	public List<Tweet> getHomeTimeline() {
		return this.getHomeTimeline(0);
	}

	@Override
	public List<Tweet> getUserTimeline(long uid, String screenName,
			long sinceId, long maxId, int count, int page, int baseApp,
			int feature, int trimUser) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		if (uid > 0) { // 参数uid与screen_name二者必选其一，且只能选其一
			statusParams.add("uid", String.valueOf(uid)); // 需要查询的用户ID。
		} else if (StringUtils.isNotEmpty(screenName)) {
			statusParams.add("screen_name", screenName); // 需要查询的用户昵称。
		}
		statusParams.add("since_id", String.valueOf(sinceId));
		statusParams.add("max_id", String.valueOf(maxId));
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		statusParams.add("base_app", String.valueOf(baseApp));
		statusParams.add("feature", String.valueOf(feature));
		statusParams.add("trim_user", String.valueOf(trimUser));
		return this.restTemplate.getForObject(
				buildUrl("statuses/user_timeline.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getUserTimeline(long uid, int count, int page,
			int feature) {
		return this.getUserTimeline(uid, "", 0, 0, count, page, 0, feature, 1);
	}

	@Override
	public List<Tweet> getUserTimeline(long uid, int feature) {
		return this.getUserTimeline(uid, 50, 1, feature);
	}

	@Override
	public List<Tweet> getUserTimeline(long uid) {
		return this.getUserTimeline(uid, 0);
	}

	@Override
	public List<Tweet> getUserTimeline(String screenName, int count, int page,
			int feature) {
		return this.getUserTimeline(0, screenName, 0, 0, count, page, 0,
				feature, 1);
	}

	@Override
	public List<Tweet> getUserTimeline(String screenName, int feature) {
		return this.getUserTimeline(screenName, 50, 1, feature);
	}

	@Override
	public List<Tweet> getUserTimeline(String screenName) {
		return this.getUserTimeline(screenName, 0);
	}

	@Override
	public List<Tweet> getUserTimeline() { // 参数uid与screen_name都没有或错误，则默认返回当前登录用户的数据
		return this.getUserTimeline(0, "", 0, 0, 50, 1, 0, 0, 0);
	}

	@Override
	public List<Tweet> getRepostTimeline(long id, long sinceId, long maxId,
			int count, int page, int baseApp, int filterByAuthor) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("id", String.valueOf(id));
		statusParams.add("since_id", String.valueOf(sinceId));
		statusParams.add("max_id", String.valueOf(maxId));
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		statusParams.add("filter_by_author", String.valueOf(filterByAuthor)); // 作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。
		return this.restTemplate.getForObject(
				buildUrl("statuses/repost_timeline.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getRepostTimeline(long id, int count, int page,
			int filterByAuthor) {
		return getRepostTimeline(id, 0, 0, count, page, 0, filterByAuthor);
	}

	@Override
	public List<Tweet> getRepostTimeline(long id, int count, int page) {
		return getRepostTimeline(id, count, page, 0);
	}

	@Override
	public List<Tweet> getRepostTimeline(long id) {
		return getRepostTimeline(id, 50, 1);
	}

	@Override
	public List<Tweet> getRepostByMe(long sinceId, long maxId, int count,
			int page) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("since_id", String.valueOf(sinceId));
		statusParams.add("max_id", String.valueOf(maxId));
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		return this.restTemplate.getForObject(
				buildUrl("statuses/repost_by_me.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getRepostByMe(int count, int page) {
		return getRepostByMe(0, 0, count, page);
	}

	@Override
	public List<Tweet> getRepostByMe() {
		return getRepostByMe(50, 1);
	}

	@Override
	public List<Tweet> getMentions(long sinceId, long maxId, int count,
			int page, int filterByAuthor, int filterBySource, int filterByType) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("since_id", String.valueOf(sinceId));
		statusParams.add("max_id", String.valueOf(maxId));
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		statusParams.add("filter_by_author", String.valueOf(filterByAuthor));
		statusParams.add("filter_by_source", String.valueOf(filterBySource));
		statusParams.add("filter_by_type", String.valueOf(filterByType));
		return this.restTemplate.getForObject(
				buildUrl("statuses/mentions.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getMentions(long sinceId, long maxId, int count, int page) {
		return getMentions(sinceId, maxId, count, page, 0, 0, 0);
	}

	@Override
	public List<Tweet> getMentions(int count, int page) {
		return getMentions(0, 0, count, page);
	}

	@Override
	public List<Tweet> getMentions() {
		return getMentions(50, 1);
	}

	@Override
	public List<Tweet> getRepostDaily(int count, int baseApp) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("count", String.valueOf(count));
		statusParams.add("base_app", String.valueOf(baseApp));
		return this.restTemplate.getForObject(
				buildUrl("statuses/hot/repost_daily.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getRepostDaily() {
		return getRepostDaily(20, 0);
	}

	@Override
	public List<Tweet> getRepostWeekly(int count, int baseApp) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("count", String.valueOf(count));
		statusParams.add("base_app", String.valueOf(baseApp));
		return this.restTemplate.getForObject(
				buildUrl("statuses/hot/repost_weekly.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getRepostWeekly() {
		return getRepostWeekly(20, 0);
	}

	@Override
	public List<Tweet> getCommentsDaily(int count, int baseApp) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("count", String.valueOf(count));
		statusParams.add("base_app", String.valueOf(baseApp));
		return this.restTemplate.getForObject(
				buildUrl("statuses/hot/comments_daily.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getCommentsDaily() {
		return getCommentsDaily(20, 0);
	}

	@Override
	public List<Tweet> getCommentsWeekly(int count, int baseApp) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("count", String.valueOf(count));
		statusParams.add("base_app", String.valueOf(baseApp));
		return this.restTemplate.getForObject(
				buildUrl("statuses/hot/comments_weekly.json", statusParams),
				TweetList.class).getStatuses();
	}

	@Override
	public List<Tweet> getCommentsWeekly() {
		return getCommentsDaily(20, 0);
	}
	
	@Override
	public List<Tweet> getHotFavoriteSuggestions(int count, int page) {
		requireAuthorization();
		MultiValueMap<String, String> statusParams = new LinkedMultiValueMap<String, String>();
		statusParams.add("count", String.valueOf(count));
		statusParams.add("page", String.valueOf(page));
		return this.restTemplate.getForObject(
				buildUrl("suggestions/favorites/hot.json", statusParams),
				TweetArrayList.class);
	}
	
	@Override
	public List<Tweet> getHotFavoriteSuggestions() {
		return getHotFavoriteSuggestions(20, 1);
	}
	
	private static class TweetArrayList extends ArrayList<Tweet> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2298872859999944055L;
		
	}
}
