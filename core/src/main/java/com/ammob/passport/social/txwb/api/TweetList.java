/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author iday
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TweetList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4520119577584829319L;

	private final List<Tweet> info;	//微博
	private final boolean hasnext;	//0-表示还有微博可拉取，1-已拉取完毕

	/**
	 * @param info
	 * @param timestamp
	 * @param hasnext
	 */
	@JsonCreator
	public TweetList(
			@JsonProperty("info") List<Tweet> info,
			@JsonProperty("hasnext") boolean hasnext) {
		this.info = info;
		this.hasnext = hasnext;
	}

	/**
	 * @return the info
	 */
	public List<Tweet> getInfo() {
		return info;
	}

	/**
	 * @return the hasnext
	 */
	public boolean isHasnext() {
		return hasnext;
	}

}
