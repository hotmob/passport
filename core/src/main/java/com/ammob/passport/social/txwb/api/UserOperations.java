/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.util.List;


/**
 * @author iday
 * 
 */
public interface UserOperations {

	public abstract TxwbProfile getUserProfile();

	public abstract List<TxwbProfile> getSpecialList(String name, int reqnum, int startindex);

	public abstract List<TxwbProfile> getSpecialList(int reqnum, int startindex);

	public abstract List<TxwbProfile> getBlackList(int reqnum, int startindex);

	public abstract List<TxwbProfile> getFollowingNames(int reqnum, int startindex);

	public abstract List<TxwbProfile> getFollowing(String name, int reqnum, int startindex);

	public abstract List<TxwbProfile> getFollowing(int reqnum, int startindex);

	public abstract List<TxwbProfile> getFollowerNames(int reqnum, int startindex);

	public abstract List<TxwbProfile> getFollowers(String name, int reqnum, int startindex);

	public abstract List<TxwbProfile> getFollowers(int reqnum, int startindex);

	public abstract void delBlackList(String name);

	public abstract void addBlackList(String name);

	public abstract void delSpecial(String name);

	public abstract void addSpecial(String name);

	public abstract void delFollowing(String name);

	public abstract void addFollowing(String...names);

	public abstract void update(String nick, int sex, int year,
			int month, int day, String countrycode, String provincecode, String citycode,
			String introduction);

	public abstract TxwbProfile getUserProfile(String screenName);

}
