/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import java.util.List;

/**
 * @author iday
 * 
 */
public interface UserOperations {
	/**
	 * 获取我的关注人中关注了指定用户的人
	 * 
	 * @param id
	 *            指定的关注目标用户UID。
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsChainFollowers(long id);

	/**
	 * 获取我的关注人中关注了指定用户的人
	 * 
	 * @param id
	 *            指定的关注目标用户UID。
	 * @param count
	 *            单页返回的记录条数
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsChainFollowers(long id,
			int count, int page);

	/**
	 * 获取用户优质粉丝列表
	 * 
	 * @param id
	 *            需要查询的用户UID。
	 * @param count
	 *            返回的记录条数，最大不超过200。
	 * @return
	 */
	public abstract List<WeiboProfile> getFollowersActive(long id, int count);

	/**
	 * 获取用户粉丝列表
	 * 
	 * @param screenName
	 *            需要查询的用户昵称。
	 * @return
	 */
	public abstract List<WeiboProfile> getFollowers(String screenName);

	/**
	 * 获取用户粉丝列表
	 * 
	 * @param id
	 *            需要查询的用户UID。
	 * @return
	 */
	public abstract List<WeiboProfile> getFollowers(long id);

	/**
	 * 获取用户粉丝列表
	 * 
	 * @param screenName
	 *            需要查询的用户昵称。
	 * @param count
	 *            单页返回的记录条数，最大不超过200。
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor
	 * @return
	 */
	public abstract List<WeiboProfile> getFollowers(String screenName,
			int count, int cursor);

	/**
	 * 获取用户粉丝列表
	 * 
	 * @param id
	 *            需要查询的用户UID。
	 * @param count
	 *            单页返回的记录条数，最大不超过200。
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor
	 * @return
	 */
	public abstract List<WeiboProfile> getFollowers(long id, int count,
			int cursor);

	/**
	 * 获取双向关注列表
	 * 
	 * @param id
	 *            需要获取双向关注列表的用户UID。
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsBilateral(long id);

	/**
	 * 获取双向关注列表
	 * 
	 * @param id
	 *            需要获取双向关注列表的用户UID。
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsBilateral(long id, int page);

	/**
	 * 获取双向关注列表
	 * 
	 * @param id
	 *            需要获取双向关注列表的用户UID。
	 * @param count
	 *            单页返回的记录条数
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsBilateral(long id, int count,
			int page);

	/**
	 * 获取双向关注列表
	 * 
	 * @param id
	 *            需要获取双向关注列表的用户UID。
	 * @param count
	 *            单页返回的记录条数
	 * @param page
	 *            返回结果的页码
	 * @param sort
	 *            排序类型，0：按关注时间最近排序
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsBilateral(long id, int count,
			int page, int sort);

	/**
	 * 获取共同关注人列表
	 * 
	 * @param id
	 *            需要获取共同关注关系的用户UID。
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsInCommon(long id);

	/**
	 * 获取共同关注人列表
	 * 
	 * @param id
	 *            需要获取共同关注关系的用户UID。
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsInCommon(long id, int page);

	/**
	 * 获取共同关注人列表
	 * 
	 * @param id
	 *            需要获取共同关注关系的用户UID。
	 * @param count
	 *            单页返回的记录条数
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsInCommon(long id, int count,
			int page);

	/**
	 * 获取共同关注人列表
	 * 
	 * @param id
	 *            需要获取共同关注关系的用户UID。
	 * @param sid
	 *            需要获取共同关注关系的另一个用户UID。
	 * @param count
	 *            单页返回的记录条数
	 * @param page
	 *            返回结果的页码
	 * @return
	 */
	public abstract List<WeiboProfile> getFriendsInCommon(long id, long sid,
			int count, int page);

	/**
	 * 获取用户的关注列表
	 * 
	 * @param screenName
	 *            需要查询的用户昵称。
	 * @return
	 */
	public abstract List<WeiboProfile> getFriends(String screenName);

	/**
	 * 获取用户的关注列表
	 * 
	 * @param id
	 *            需要查询的用户UID。
	 * @return
	 */
	public abstract List<WeiboProfile> getFriends(long id);

	/**
	 * 获取用户的关注列表
	 * 
	 * @param screenName
	 *            需要查询的用户昵称。
	 * @param count
	 *            单页返回的记录条数，最大不超过200。
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor
	 * @return
	 */
	public abstract List<WeiboProfile> getFriends(String screenName, int count,
			int cursor);

	/**
	 * 获取用户的关注列表
	 * 
	 * @param id
	 *            需要查询的用户UID。
	 * @param count
	 *            单页返回的记录条数，最大不超过200。
	 * @param cursor
	 *            返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor
	 * @return
	 */
	public abstract List<WeiboProfile> getFriends(long id, int count, int cursor);

	/**
	 * 取消关注某用户
	 * 
	 * @param screenName
	 *            需要取消关注的用户昵称。
	 * @return
	 */
	public abstract WeiboProfile destroyFriendship(String screenName);

	/**
	 * 取消关注某用户
	 * 
	 * @param id
	 *            需要取消关注的用户UID。
	 * @return
	 */
	public abstract WeiboProfile destroyFriendship(long id);

	/**
	 * 关注某用户
	 * 
	 * @param screenName
	 *            需要关注的用户昵称。
	 * @return
	 */
	public abstract WeiboProfile createFriendship(String screenName);

	/**
	 * 关注某用户
	 * 
	 * @param id
	 *            需要关注的用户ID。
	 * @return
	 */
	public abstract WeiboProfile createFriendship(long id);

	/**
	 * 获取当前用户信息
	 * 
	 * @return
	 */
	public abstract WeiboProfile getUserProfile();

	/**
	 * 获取用户信息
	 * 
	 * @param screenName
	 *            需要查询的用户昵称。
	 * @return
	 */
	public abstract WeiboProfile getUserProfile(String screenName);

	/**
	 * 获取用户信息
	 * 
	 * @param uid
	 *            需要查询的用户ID。
	 * @return
	 */
	public abstract WeiboProfile getUserProfile(long uid);

	/**
	 * 根据微博内容推荐用户
	 * @param content 微博正文内容，必须做URLEncode，UTF-8编码 。
	 * @return
	 */
	public abstract List<WeiboProfile> getUserSuggestionsByStatus(String content);

	/**
	 * 根据微博内容推荐用户
	 * @param content 微博正文内容，必须做URLEncode，UTF-8编码 。
	 * @param count 返回结果数目
	 * @return
	 */
	public abstract List<WeiboProfile> getUserSuggestionsByStatus(
			String content, int count);

	/**
	 * 获取系统推荐用户
	 * 
	 * @return
	 */
	public abstract List<WeiboProfile> getHotUserSuggestions();

	/**
	 * 获取系统推荐用户
	 * 
	 * @param category
	 *            推荐分类，返回某一类别的推荐用户。如果不在以下分类中，返回空列表。default：人气关注、
	 *            ent：影视名星、music：音乐、fashion：时尚、literature：文学、business：商界、
	 *            sports：体育、health：健康、auto：汽车、house：房产、trip：旅行、stock：炒股、
	 *            food：美食、fate：命理、art：艺术、tech：科技、cartoon：动漫、games：游戏。
	 * @return
	 */
	public abstract List<WeiboProfile> getHotUserSuggestions(String category);
	/**
	 * 把某人标识为不感兴趣的人
	 * @param id 不感兴趣的用户的UID。
	 * @return
	 */
	public abstract WeiboProfile notInterestedUser(long id);

}
