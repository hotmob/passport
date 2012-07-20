/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.io.Serializable;

/**
 * @author iday
 *
 */
public class Music implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4535626217268461983L;
	
	private final String author;	//演唱者
	private final String url;	//音频地址
	private final String title;	//音频名字，歌名
	/**
	 * @param author
	 * @param url
	 * @param title
	 */
	public Music(String author, String url, String title) {
		this.author = author;
		this.url = url;
		this.title = title;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	
}
