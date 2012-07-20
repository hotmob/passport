package com.ammob.passport.social.weibo.api;

import java.util.List;

import org.springframework.core.io.Resource;


public interface TimelineOperations {
	
	/**
	 * 按周返回热门评论榜
	 * @return
	 */
	public abstract List<Tweet> getCommentsWeekly();
	/**
	 * 按周返回热门评论榜
	 * @param count 返回的记录条数，最大不超过50。
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @return
	 */
	public abstract List<Tweet> getCommentsWeekly(int count, int baseApp);
	/**
	 * 按天返回热门评论榜
	 * @return
	 */
	public abstract List<Tweet> getCommentsDaily();
	/**
	 * 按天返回热门评论榜
	 * @param count 返回的记录条数，最大不超过50。
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @return
	 */
	public abstract List<Tweet> getCommentsDaily(int count, int baseApp);
	/**
	 * 按周返回热门转发榜
	 * @return
	 */
	public abstract List<Tweet> getRepostWeekly();
	/**
	 * 按周返回热门转发榜
	 * @param count 返回的记录条数，最大不超过50。
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @return
	 */
	public abstract List<Tweet> getRepostWeekly(int count, int baseApp);
	/**
	 * 按天返回热门转发榜
	 * @return
	 */
	public abstract List<Tweet> getRepostDaily();
	/**
	 * 按天返回热门转发榜
	 * @param count 返回的记录条数，最大不超过50。
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @return
	 */
	public abstract List<Tweet> getRepostDaily(int count, int baseApp);
	/**
	 * 获取@当前用户的最新微博
	 * @return
	 */
	public abstract List<Tweet> getMentions();
	/**
	 * 获取@当前用户的最新微博
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @return
	 */
	public abstract List<Tweet> getMentions(int count, int page);
	/**
	 * 获取@当前用户的最新微博
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @return
	 */
	public abstract List<Tweet> getMentions(long sinceId, long maxId, int count,
			int page);
	/**
	 * 获取@当前用户的最新微博
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param filterByAuthor 作者筛选类型，0：全部、1：我关注的人、2：陌生人。
	 * @param filterBySource 来源筛选类型，0：全部、1：来自微博、2：来自微群。 
	 * @param filterByType 原创筛选类型，0：全部微博、1：原创的微博。
	 * @return
	 */
	public abstract List<Tweet> getMentions(long sinceId, long maxId, int count,
			int page, int filterByAuthor, int filterBySource, int filterByType);
	/**
	 * 返回用户转发的最新微博
	 * @return
	 */
	public abstract List<Tweet> getRepostByMe();
	/**
	 * 返回用户转发的最新微博
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @return
	 */
	public abstract List<Tweet> getRepostByMe(int count, int page);
	/**
	 * 返回用户转发的最新微博
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @return
	 */
	public abstract List<Tweet> getRepostByMe(long sinceId, long maxId,
			int count, int page);
	/**
	 * 返回一条原创微博的最新转发微博
	 * @param id 需要查询的微博ID。
	 * @return
	 */
	public abstract List<Tweet> getRepostTimeline(long id);
	/**
	 * 返回一条原创微博的最新转发微博
	 * @param id 需要查询的微博ID。
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @return
	 */
	public abstract List<Tweet> getRepostTimeline(long id, int count,
			int page);
	/**
	 * 返回一条原创微博的最新转发微博
	 * @param id 需要查询的微博ID。
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param filterByAuthor 作者筛选类型，0：全部、1：我关注的人、2：陌生人。
	 * @return
	 */
	public abstract List<Tweet> getRepostTimeline(long id, int count,
			int page, int filterByAuthor);
	/**
	 * 返回一条原创微博的最新转发微博
	 * @param id 需要查询的微博ID。
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @param filterByAuthor 作者筛选类型，0：全部、1：我关注的人、2：陌生人。
	 * @return
	 */
	public abstract List<Tweet> getRepostTimeline(long id, long sinceId,
			long maxId, int count, int page, int baseApp, int filterByAuthor);
	/**
	 * 获取用户发布的微博
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline();
	/**
	 * 获取用户发布的微博
	 * @param screenName 需要查询的用户昵称。 
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(String screenName);
	/**
	 * 获取用户发布的微博
	 * @param screenName 需要查询的用户昵称。 
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(String screenName, int feature);
	/**
	 * 获取用户发布的微博
	 * @param screenName 需要查询的用户昵称。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(String screenName, int count,
			int page, int feature);
	/**
	 * 获取用户发布的微博
	 * @param uid 需要查询的用户ID。 
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(long uid);
	/**
	 * 获取用户发布的微博
	 * @param uid 需要查询的用户ID。 
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(long uid, int feature);
	/**
	 * 获取用户发布的微博
	 * @param uid 需要查询的用户ID。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(long uid, int count,
			int page, int feature);
	/**
	 * 获取用户发布的微博
	 * @param uid 需要查询的用户ID。 
	 * @param screenName 需要查询的用户昵称。
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @param trimUser 返回值中user信息开关，0：返回完整的user信息、1：user字段仅返回user_id。
	 * @return
	 */
	public abstract List<Tweet> getUserTimeline(long uid, String screenName,
			long sinceId, long maxId, int count, int page, int baseApp,
			int feature, int trimUser);
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @return
	 */
	public abstract List<Tweet> getHomeTimeline();
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getHomeTimeline(int feature);
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getHomeTimeline(int count, int page,
			int feature);
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getHomeTimeline(long sinceId, long maxId,
			int count, int page, int baseApp, int feature);
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @return
	 */
	public abstract List<Tweet> getFriendsTimeline();
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getFriendsTimeline(int feature);
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getFriendsTimeline(int count, int page,
			int feature);
	/**
	 * 获取当前登录用户及其所关注用户的最新微博
	 * @param sinceId 返回ID比since_id大的微博（即比since_id时间晚的微博）。 
	 * @param maxId 返回ID小于或等于max_id的微博。 
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @param feature 过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐。可以组合，比如：12：原创图片。
	 * @return
	 */
	public abstract List<Tweet> getFriendsTimeline(long sinceId, long maxId,
			int count, int page, int baseApp, int feature);
	/**
	 * 获取最新的公共微博
	 * @return
	 */
	public abstract List<Tweet> getPublicTimeline();
	/**
	 * 获取最新的公共微博
	 * @param count 单页返回的记录条数。 
	 * @param page 返回结果的页码。 
	 * @param baseApp 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用）。
	 * @return
	 */
	public abstract List<Tweet> getPublicTimeline(int count, int page,
			int baseApp);
	/**
	 * 转发一条微博信息
	 * @param id 要转发的微博ID。
	 * @return
	 */
	public abstract Tweet repostStatus(long id);
	/**
	 * 转发一条微博信息
	 * @param id 要转发的微博ID。 
	 * @param message 添加的转发文本，必须做URLencode，内容不超过140个汉字。
	 * @param comment 是否在转发的同时发表评论，0：否、1：评论给当前微博、2：评论给原微博、3：都评论。 
	 * @return
	 */
	public abstract Tweet repostStatus(long id, String message, int comment);
	/**
	 * 删除微博信息
	 * @param id 需要删除的微博ID。 
	 * @return
	 */
	public abstract Tweet destroyStatus(long id);
	/**
	 * 根据ID获取单条微博信息
	 * @param id 需要获取的微博ID。 
	 * @return
	 */
	public abstract Tweet getStatus(long id);
	/**
	 * 发布一条微博信息
	 * @param message 要发布的微博文本内容，必须做URLencode，内容不超过140个汉字。 
	 * @return
	 */
	public abstract Tweet updateStatus(String message);
	/**
	 * 上传图片并发布一条微博
	 * @param message 要发布的微博文本内容，必须做URLencode，内容不超过140个汉字。 
	 * @param image 要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
	 * @return
	 */
	public abstract Tweet updateStatus(String message, Resource image);
	/**
	 * 上传图片并发布一条微博
	 * @param message 要发布的微博文本内容，必须做URLencode，内容不超过140个汉字。 
	 * @param image 要上传的图片，仅支持JPEG、GIF、PNG格式，图片大小小于5M。
	 * @param geo 位置信息
	 * @return
	 */
	public abstract Tweet updateStatus(String message, Resource image, Geo geo);
	/**
	 * 发布一条微博信息
	 * @param message 要发布的微博文本内容，必须做URLencode，内容不超过140个汉字。 
	 * @param geo 位置信息
	 * @return
	 */
	public abstract Tweet updateStatus(String message, Geo geo);
	/**
	 * 热门收藏
	 * @return
	 */
	public abstract List<Tweet> getHotFavoriteSuggestions();
	/**
	 * 热门收藏
	 * @param count 每页返回结果数
	 * @param page 返回页码
	 * @return
	 */
	public abstract List<Tweet> getHotFavoriteSuggestions(int count, int page);

}