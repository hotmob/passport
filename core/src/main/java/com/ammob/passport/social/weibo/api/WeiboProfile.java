/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @author iday
 * 
 */
public class WeiboProfile implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5116269573848009800L;

	private final long id;
	private final String name;
	private final String screenName;
	private final String imageUrl;
	private final String domain;
	private final String url;
	private final int province;//用户所在地区ID
	private final int city;//用户所在城市ID
	private final String location;//用户所在地
	private final String description;//用户描述 
	private final String gender;//性别，m：男、f：女、n：未知
	private final int followersCount;//粉丝数
	private final int friendsCount;//关注数
	private final int statusesCount;//微博数
	private final int favouritesCount;//收藏数
	private final Date createdAt;//创建时间
	private final boolean following;//当前登录用户是否已关注该用户
	private final boolean allowAllActMsg;//是否允许所有人给我发私信
	private final boolean geoEnabled;// 	是否允许带有地理信息
	private final boolean verified;// 	是否是微博认证用户，即带V用户
	private final boolean allowAllComment;//是否允许所有人对我的微博进行评论
	private final String avatarLarge;//用户大头像地址
	private final String verifiedReason;//认证原因
	private final boolean followMe;//该用户是否关注当前登录用户
	private final int onlineStatus;//用户的在线状态，0：不在线、1：在线
	private final int biFollowersCount;//用户的互粉数
	private final Tweet status;//用户的最近一条微博信息字段 


	/**
	 * @param id
	 * @param name
	 * @param screenName
	 * @param imageUrl
	 * @param domain
	 * @param url
	 * @param province
	 * @param city
	 * @param location
	 * @param description
	 * @param gender
	 * @param followersCount
	 * @param friendsCount
	 * @param statusesCount
	 * @param favouritesCount
	 * @param createdAt
	 * @param following
	 * @param allowAllActMsg
	 * @param geoEnabled
	 * @param verified
	 * @param allowAllComment
	 * @param avatarLarge
	 * @param verifiedReason
	 * @param followMe
	 * @param onlineStatus
	 * @param biFollowersCount
	 * @param status
	 */
	public WeiboProfile(long id, String name, String screenName,
			String imageUrl, String domain, String url, int province, int city,
			String location, String description, String gender,
			int followersCount, int friendsCount, int statusesCount,
			int favouritesCount, Date createdAt, boolean following,
			boolean allowAllActMsg, boolean geoEnabled, boolean verified,
			boolean allowAllComment, String avatarLarge, String verifiedReason,
			boolean followMe, int onlineStatus, int biFollowersCount,
			Tweet status) {
		this.id = id;
		this.name = name;
		this.screenName = screenName;
		this.imageUrl = imageUrl;
		this.domain = domain;
		this.url = url;
		this.province = province;
		this.city = city;
		this.location = location;
		this.description = description;
		this.gender = gender;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.statusesCount = statusesCount;
		this.favouritesCount = favouritesCount;
		this.createdAt = createdAt;
		this.following = following;
		this.allowAllActMsg = allowAllActMsg;
		this.geoEnabled = geoEnabled;
		this.verified = verified;
		this.allowAllComment = allowAllComment;
		this.avatarLarge = avatarLarge;
		this.verifiedReason = verifiedReason;
		this.followMe = followMe;
		this.onlineStatus = onlineStatus;
		this.biFollowersCount = biFollowersCount;
		this.status = status;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @return the province
	 */
	public int getProvince() {
		return province;
	}

	/**
	 * @return the city
	 */
	public int getCity() {
		return city;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the followersCount
	 */
	public int getFollowersCount() {
		return followersCount;
	}

	/**
	 * @return the friendsCount
	 */
	public int getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @return the statusesCount
	 */
	public int getStatusesCount() {
		return statusesCount;
	}

	/**
	 * @return the favouritesCount
	 */
	public int getFavouritesCount() {
		return favouritesCount;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the following
	 */
	public boolean isFollowing() {
		return following;
	}

	/**
	 * @return the allowAllActMsg
	 */
	public boolean isAllowAllActMsg() {
		return allowAllActMsg;
	}

	/**
	 * @return the geoEnabled
	 */
	public boolean isGeoEnabled() {
		return geoEnabled;
	}

	/**
	 * @return the verified
	 */
	public boolean isVerified() {
		return verified;
	}

	/**
	 * @return the allowAllComment
	 */
	public boolean isAllowAllComment() {
		return allowAllComment;
	}

	/**
	 * @return the avatarLarge
	 */
	public String getAvatarLarge() {
		return avatarLarge;
	}

	/**
	 * @return the verifiedReason
	 */
	public String getVerifiedReason() {
		return verifiedReason;
	}

	/**
	 * @return the followMe
	 */
	public boolean isFollowMe() {
		return followMe;
	}

	/**
	 * @return the onlineStatus
	 */
	public int getOnlineStatus() {
		return onlineStatus;
	}

	/**
	 * @return the biFollowersCount
	 */
	public int getBiFollowersCount() {
		return biFollowersCount;
	}

	/**
	 * @return the status
	 */
	public Tweet getStatus() {
		return status;
	}

}
