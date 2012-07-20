/**
 * 
 */
package com.ammob.passport.social.renren.api;

/**
 * @author iday
 * 
 */
public class StatusPost extends Post {

	/**
	 * 
	 */
	private static final long serialVersionUID = 680299583402643844L;

	private Place place;

	public StatusPost(long id, Reference user, Source source,
			long viewCount, long commentCount) {
		super(id, user, source, viewCount, commentCount);
		place = null;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

}
