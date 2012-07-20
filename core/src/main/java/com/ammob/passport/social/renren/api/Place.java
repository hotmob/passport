/**
 * 
 */
package com.ammob.passport.social.renren.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class Place implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3226471446590847715L;

	private final long id;
	private final String name;
	private final String address;
	private final String url;
	private final double longtitude;
	private final double latitude;

	/**
	 * @param id
	 * @param name
	 * @param address
	 * @param url
	 * @param longtitude
	 * @param latitude
	 */
	public Place(long id, String name, String address, String url,
			double longtitude, double latitude) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.url = url;
		this.longtitude = longtitude;
		this.latitude = latitude;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the longtitude
	 */
	public double getLongtitude() {
		return longtitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

}
