/**
 * 
 */
package com.ammob.passport.social.weibo.api.v2;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ammob.passport.social.weibo.api.Favorite;
import com.ammob.passport.social.weibo.api.Tag;
import com.ammob.passport.social.weibo.api.Weibo;

/**
 * @author iday
 * 
 */
public class FavoriteTemplate extends AbstractWeiboOperations implements
		FavoriteOperations {

	public FavoriteTemplate(Weibo api, RestTemplate restTemplate,
			boolean isAuthorized) {
		super(api, restTemplate, isAuthorized);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ammob.passport.social.weibo.api.v2.FavoriteOperations#create(long)
	 */
	@Override
	public Favorite create(long id) {
		requireAuthorization();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("id", id);
		return restTemplate.postForObject(buildUrl("favorites/create.json"),
				params, Favorite.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ammob.passport.social.weibo.api.v2.FavoriteOperations#destroy(long)
	 */
	@Override
	public Favorite destroy(long id) {
		requireAuthorization();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("id", id);
		return restTemplate.postForObject(buildUrl("favorites/destroy.json"),
				params, Favorite.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#destroy(java.lang
	 * .Long)
	 */
	@Override
	public boolean destroy(Long... ids) {
		requireAuthorization();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("ids", StringUtils.join(ids));
		return restTemplate
				.postForObject(buildUrl("favorites/destroy_batch.json"),
						params, JsonNode.class).get("result").asBoolean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#updateTags(long,
	 * java.lang.String)
	 */
	@Override
	public Favorite updateTags(long id, String tags) {
		requireAuthorization();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("id", id);
		params.add("tags", tags);
		return restTemplate.postForObject(
				buildUrl("favorites/tags/update.json"), params, Favorite.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#updateTagsBatch
	 * (long, java.lang.String)
	 */
	@Override
	public Tag updateTagsBatch(long id, String tag) {
		requireAuthorization();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("id", id);
		params.add("tag", tag);
		return restTemplate
				.postForObject(buildUrl("favorites/tags/update_batch.json"),
						params, Tag.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#destroyTags(long)
	 */
	@Override
	public boolean destroyTags(long id) {
		requireAuthorization();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("id", id);
		return restTemplate
				.postForObject(buildUrl("favorites/tags/destroy_batch.json"),
						params, JsonNode.class).get("result").asBoolean();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getFavorites(int,
	 * int)
	 */
	@Override
	public List<Favorite> getFavorites(int count, int page) {
		requireAuthorization();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("count", String.valueOf(count));
		params.add("page", String.valueOf(page));
		return restTemplate.getForObject(buildUrl("favorites.json", params),
				FavoriteList.class).getFavorites();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getFavorites()
	 */
	@Override
	public List<Favorite> getFavorites() {
		return getFavorites(50, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getFavorite(long)
	 */
	@Override
	public Favorite getFavorite(long id) {
		requireAuthorization();
		return restTemplate.getForObject(
				buildUrl("favorites/show.json", "id", String.valueOf(id)),
				Favorite.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getFavoritesByTags
	 * (long, int, int)
	 */
	@Override
	public List<Favorite> getFavoritesByTags(long id, int count, int page) {
		requireAuthorization();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("id", String.valueOf(id));
		params.add("count", String.valueOf(count));
		params.add("page", String.valueOf(page));
		return restTemplate.getForObject(
				buildUrl("favorites/by_tags.json", params), FavoriteList.class)
				.getFavorites();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getFavoritesByTags
	 * (long)
	 */
	@Override
	public List<Favorite> getFavoritesByTags(long id) {
		return getFavoritesByTags(id, 50, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getTags(int,
	 * int)
	 */
	@Override
	public List<Tag> getTags(int count, int page) {
		requireAuthorization();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("count", String.valueOf(count));
		params.add("page", String.valueOf(page));
		return restTemplate.getForObject(
				buildUrl("favorites/tags.json", params), TagList.class)
				.getTags();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ammob.passport.social.weibo.api.v2.FavoriteOperations#getTags()
	 */
	@Override
	public List<Tag> getTags() {
		return getTags(10, 1);
	}

}
