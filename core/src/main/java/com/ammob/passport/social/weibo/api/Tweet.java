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
public class Tweet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7772176642997716355L;

	private Date createdAt; // 创建时间
	private long id; // 微博ID
	private String text; // 微博信息内容
	private String source; // 微博来源
	private boolean favorited; // 是否已收藏(正在开发中，暂不支持)
	private boolean truncated; // 是否被截断
	private long inReplyToStatusId; // 回复ID
	private long inReplyToUserId; // 回复人UID
	private String inReplyToScreenName; // 回复人昵称
	private String thumbnailPic; // 缩略图
	private String bmiddlePic; // 中型图片
	private String originalPic; // 原始图片
	private Geo geo;
	private WeiboProfile user; // 作者信息
	private Tweet retweetedStatus; // 转发的博文，内容为status，如果不是转发，则没有此字段
	private long repostsCount; // 转发数
	private long commentsCount; // 评论数

	/**
	 * @return the geo
	 */
	public Geo getGeo() {
		return geo;
	}

	/**
	 * @param geo the geo to set
	 */
	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	/**
	 * @param createdAt
	 * @param id
	 * @param text
	 * @param source
	 * @param favorited
	 * @param truncated
	 * @param inReplyToStatusId
	 * @param inReplyToUserId
	 * @param inReplyToScreenName
	 * @param user
	 * @param repostsCount
	 * @param commentsCount
	 */
	public Tweet(Date createdAt, long id, String text, String source,
			boolean favorited, boolean truncated, long inReplyToStatusId,
			long inReplyToUserId, String inReplyToScreenName,
			WeiboProfile user, int repostsCount, int commentsCount) {
		this.createdAt = createdAt;
		this.id = id;
		this.text = text;
		this.source = source;
		this.favorited = favorited;
		this.truncated = truncated;
		this.inReplyToStatusId = inReplyToStatusId;
		this.inReplyToUserId = inReplyToUserId;
		this.inReplyToScreenName = inReplyToScreenName;
		this.user = user;
		this.repostsCount = repostsCount;
		this.commentsCount = commentsCount;
	}

	/**
	 * @return the inReplyToStatusId
	 */
	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	/**
	 * @param inReplyToStatusId
	 *            the inReplyToStatusId to set
	 */
	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	/**
	 * @return the inReplyToUserId
	 */
	public long getInReplyToUserId() {
		return inReplyToUserId;
	}

	/**
	 * @param inReplyToUserId
	 *            the inReplyToUserId to set
	 */
	public void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	/**
	 * @return the inReplyToScreenName
	 */
	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}

	/**
	 * @param inReplyToScreenName
	 *            the inReplyToScreenName to set
	 */
	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}

	/**
	 * @return the user
	 */
	public WeiboProfile getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(WeiboProfile user) {
		this.user = user;
	}

	/**
	 * @return the retweetedStatus
	 */
	public Tweet getRetweetedStatus() {
		return retweetedStatus;
	}

	/**
	 * @param retweetedStatus
	 *            the retweetedStatus to set
	 */
	public void setRetweetedStatus(Tweet retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}

	/**
	 * @return the repostsCount
	 */
	public long getRepostsCount() {
		return repostsCount;
	}

	/**
	 * @param repostsCount
	 *            the repostsCount to set
	 */
	public void setRepostsCount(long repostsCount) {
		this.repostsCount = repostsCount;
	}

	/**
	 * @return the commentsCount
	 */
	public long getCommentsCount() {
		return commentsCount;
	}

	/**
	 * @param commentsCount
	 *            the commentsCount to set
	 */
	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	/**
	 * @return the created
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreatedAt(Date created) {
		this.createdAt = created;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the favorited
	 */
	public boolean isFavorited() {
		return favorited;
	}

	/**
	 * @param favorited
	 *            the favorited to set
	 */
	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	/**
	 * @return the truncated
	 */
	public boolean isTruncated() {
		return truncated;
	}

	/**
	 * @param truncated
	 *            the truncated to set
	 */
	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	/**
	 * @return the thumbnailPic
	 */
	public String getThumbnailPic() {
		return thumbnailPic;
	}

	/**
	 * @param thumbnailPic
	 *            the thumbnailPic to set
	 */
	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	/**
	 * @return the bmiddlePic
	 */
	public String getBmiddlePic() {
		return bmiddlePic;
	}

	/**
	 * @param bmiddlePic
	 *            the bmiddlePic to set
	 */
	public void setBmiddlePic(String bmiddlePic) {
		this.bmiddlePic = bmiddlePic;
	}

	/**
	 * @return the originalPic
	 */
	public String getOriginalPic() {
		return originalPic;
	}

	/**
	 * @param originalPic
	 *            the originalPic to set
	 */
	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

}
