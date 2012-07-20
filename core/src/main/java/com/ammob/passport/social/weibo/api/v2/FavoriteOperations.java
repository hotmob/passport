package com.ammob.passport.social.weibo.api.v2;

import java.util.List;

import com.ammob.passport.social.weibo.api.Favorite;
import com.ammob.passport.social.weibo.api.Tag;

public interface FavoriteOperations {
	/**
	 * 添加收藏
	 * @param id 要收藏的微博ID。 
	 * @return
	 */
	public abstract Favorite create(long id);
	/**
	 * 删除收藏
	 * @param id 要取消收藏的微博ID。 
	 * @return
	 */
	public abstract Favorite destroy(long id);
	/**
	 * 批量删除收藏
	 * @param ids 要取消收藏的收藏ID，用半角逗号分隔，最多不超过10个。 
	 * @return
	 */
	public abstract boolean destroy(Long... ids);
	/**
	 * 更新收藏标签
	 * @param id 需要更新的收藏ID。
	 * @param tags 需要更新的标签内容，必须做URLencode，用半角逗号分隔，最多不超过2条。
	 * @return
	 */
	public abstract Favorite updateTags(long id, String tags);
	/**
	 * 更新当前用户所有收藏下的指定标签
	 * @param id 需要更新的标签ID。
	 * @param tag 需要更新的标签内容，必须做URLencode。
	 * @return
	 */
	public abstract Tag updateTagsBatch(long id, String tag);
	/**
	 * 删除当前用户所有收藏下的指定标签
	 * @param id 需要删除的标签ID。 
	 * @return
	 */
	public abstract boolean destroyTags(long id);
	/**
	 * 获取当前用户的收藏列表
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Favorite> getFavorites(int count, int page);
	/**
	 * 获取当前用户的收藏列表
	 * @return
	 */
	public abstract List<Favorite> getFavorites();
	/**
	 * 获取单条收藏信息
	 * @param id 需要查询的收藏ID。 
	 * @return
	 */
	public abstract Favorite getFavorite(long id);
	/**
	 * 获取当前用户某个标签下的收藏列表
	 * @param id 需要查询的标签ID。
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Favorite> getFavoritesByTags(long id, int count,
			int page);
	/**
	 * 获取当前用户某个标签下的收藏列表
	 * @param id 需要查询的标签ID。
	 * @return
	 */
	public abstract List<Favorite> getFavoritesByTags(long id);
	/**
	 * 当前登录用户的收藏标签列表
	 * @param count 单页返回的记录条数
	 * @param page 返回结果的页码
	 * @return
	 */
	public abstract List<Tag> getTags(int count, int page);
	/**
	 * 当前登录用户的收藏标签列表
	 * @return
	 */
	public abstract List<Tag> getTags();

}