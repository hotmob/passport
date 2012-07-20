/**
 * 
 */
package com.ammob.passport.social.weibo.api;

import java.io.Serializable;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author iday
 * 
 */
public class Geo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2940983175828008753L;
	
	private Double latitude;
	private Double longitude;
	private int city;
	private int province;
	private String cityName;
	private String provinceName;
	private String address;
	private String pinyin;
	private String more;

	/**
	 * @return the city
	 */
	public int getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(int city) {
		this.city = city;
	}

	/**
	 * @return the province
	 */
	public int getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(int province) {
		this.province = province;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}

	/**
	 * @param pinyin the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	/**
	 * @return the more
	 */
	public String getMore() {
		return more;
	}

	/**
	 * @param more the more to set
	 */
	public void setMore(String more) {
		this.more = more;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 
	 */
	public Geo() {
	}

	/**
	 * @param latitude
	 * @param longitude
	 */
	public Geo(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public MultiValueMap<String, Object> toParameterMap() {
		LinkedMultiValueMap<String, Object> parameterMap = new LinkedMultiValueMap<String, Object>();
		if (latitude != null && longitude != null) {
			parameterMap.set("lat", latitude.toString());
			parameterMap.set("long", longitude.toString());
		}
		return parameterMap;
	}

}
