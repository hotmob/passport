/**
 * 
 */
package com.ammob.passport.social.txwb.api;

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
	private static final long serialVersionUID = 6539280415997167406L;

	private final String text; // 微博内容
	private final String orgtext; // 原始内容
	private final long count; // 微博被转次数
	private final long mcount; // 点评次数
	private final String from; // 来源
	private final String fromurl; // 来源url
	private final long id; // 微博唯一id
	private final String[] image; // 图片url列表
	private final Video video; // 视频信息
	private final Music music; // 音频信息
	private final String name; // 发表人帐户名
	private final String openid; // 用户唯一id，与name相对应
	private final String nick; // 发表人昵称
	private final boolean self; // 是否自已发的的微博
	private final Date timestamp; // 发表时间
	private final int type; // 微博类型，1-原创发表，2-转载，3-私信，4-回复，5-空回，6-提及，7-评论
	private final String head; // 发表者头像url
	private final String location; // 发表者所在地
	private final String countrycode; // 国家码
	private final String provincecode; // 省份码
	private final String citycode; // 城市码
	private final boolean vip; // 是否微博认证用户
	private final String geo;
	private final TweetStatus status; // 微博状态
	private final String emotionurl; // 心情图片url
	private final String emotiontype; // 心情类型
	private final Tweet source; // 当type=2时，source即为源tweet

	/**
	 * @param text
	 * @param orgtext
	 * @param count
	 * @param mcount
	 * @param from
	 * @param fromurl
	 * @param id
	 * @param image
	 * @param video
	 * @param music
	 * @param name
	 * @param openid
	 * @param nick
	 * @param self
	 * @param timestamp
	 * @param type
	 * @param head
	 * @param location
	 * @param countrycode
	 * @param provincecode
	 * @param citycode
	 * @param vip
	 * @param geo
	 * @param status
	 * @param emotionurl
	 * @param emotiontype
	 * @param source
	 */
	public Tweet(String text, String orgtext, long count, long mcount,
			String from, String fromurl, long id, String[] image, Video video,
			Music music, String name, String openid, String nick, boolean self,
			Date timestamp, int type, String head, String location,
			String countrycode, String provincecode, String citycode,
			boolean vip, String geo, TweetStatus status, String emotionurl,
			String emotiontype, Tweet source) {
		this.text = text;
		this.orgtext = orgtext;
		this.count = count;
		this.mcount = mcount;
		this.from = from;
		this.fromurl = fromurl;
		this.id = id;
		this.image = image;
		this.video = video;
		this.music = music;
		this.name = name;
		this.openid = openid;
		this.nick = nick;
		this.self = self;
		this.timestamp = timestamp;
		this.type = type;
		this.head = head;
		this.location = location;
		this.countrycode = countrycode;
		this.provincecode = provincecode;
		this.citycode = citycode;
		this.vip = vip;
		this.geo = geo;
		this.status = status;
		this.emotionurl = emotionurl;
		this.emotiontype = emotiontype;
		this.source = source;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the orgtext
	 */
	public String getOrgtext() {
		return orgtext;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @return the mcount
	 */
	public long getMcount() {
		return mcount;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @return the fromurl
	 */
	public String getFromurl() {
		return fromurl;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the image
	 */
	public String[] getImage() {
		return image;
	}

	/**
	 * @return the video
	 */
	public Video getVideo() {
		return video;
	}

	/**
	 * @return the music
	 */
	public Music getMusic() {
		return music;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @return the self
	 */
	public boolean isSelf() {
		return self;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the head
	 */
	public String getHead() {
		return head;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the countrycode
	 */
	public String getCountrycode() {
		return countrycode;
	}

	/**
	 * @return the provincecode
	 */
	public String getProvincecode() {
		return provincecode;
	}

	/**
	 * @return the citycode
	 */
	public String getCitycode() {
		return citycode;
	}

	/**
	 * @return the vip
	 */
	public boolean isVip() {
		return vip;
	}

	/**
	 * @return the geo
	 */
	public String getGeo() {
		return geo;
	}

	/**
	 * @return the status
	 */
	public TweetStatus getStatus() {
		return status;
	}

	/**
	 * @return the emotionurl
	 */
	public String getEmotionurl() {
		return emotionurl;
	}

	/**
	 * @return the emotiontype
	 */
	public String getEmotiontype() {
		return emotiontype;
	}

	/**
	 * @return the source
	 */
	public Tweet getSource() {
		return source;
	}

}
