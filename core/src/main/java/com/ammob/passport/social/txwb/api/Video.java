/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7046263657284495106L;

	private final String picurl;	//缩略图
	private final String player;	//播放器地址
	private final String realurl;	//视频原地址
	private final String shoturl;	//视频的短url
	private final String title;	//视频标题

	/**
	 * @param picurl
	 * @param player
	 * @param realurl
	 * @param shoturl
	 * @param title
	 */
	public Video(String picurl, String player, String realurl, String shoturl,
			String title) {
		this.picurl = picurl;
		this.player = player;
		this.realurl = realurl;
		this.shoturl = shoturl;
		this.title = title;
	}

	/**
	 * @return the picurl
	 */
	public String getPicurl() {
		return picurl;
	}

	/**
	 * @return the player
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * @return the realurl
	 */
	public String getRealurl() {
		return realurl;
	}

	/**
	 * @return the shoturl
	 */
	public String getShoturl() {
		return shoturl;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

}
