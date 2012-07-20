/**
 * 
 */
package com.ammob.passport.social.txwb.api;

import java.io.Serializable;

/**
 * @author iday
 * 
 */
public class TxwbProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4313445178151107652L;
	private final String name;	//用户帐户名
	private final String openId;	//用户唯一id，与name相对应
	private final String nick;	//用户昵称
	private final String head;	//头像url
	private final String location;	//所在地
	private final boolean vip;	//是否认证用户
	private final boolean ent;	//是否企业机构
	private final String introduction;	//个人介绍
	private final String verifyinfo;	//认证信息
	private final String email;	//邮箱
	private final int sex;	//用户性别，1-男，2-女，0-未填写
	private final long fnasnum;	//听众数
	private final long idolnum;	//收听的人数
	private final long tweetnum;	//发表的微博数

	/**
	 * @param name
	 * @param openId
	 * @param nick
	 * @param head
	 * @param location
	 * @param vip
	 * @param ent
	 * @param introduction
	 * @param verifyinfo
	 * @param email
	 * @param sex
	 * @param fnasnum
	 * @param idolnum
	 * @param tweetnum
	 */
	public TxwbProfile(String name, String openId, String nick, String head,
			String location, boolean vip, boolean ent, String introduction,
			String verifyinfo, String email, int sex, long fnasnum,
			long idolnum, long tweetnum) {
		this.name = name;
		this.openId = openId;
		this.nick = nick;
		this.head = head;
		this.location = location;
		this.vip = vip;
		this.ent = ent;
		this.introduction = introduction;
		this.verifyinfo = verifyinfo;
		this.email = email;
		this.sex = sex;
		this.fnasnum = fnasnum;
		this.idolnum = idolnum;
		this.tweetnum = tweetnum;
	}

	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
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
	 * @return the vip
	 */
	public boolean isVip() {
		return vip;
	}

	/**
	 * @return the ent
	 */
	public boolean isEnt() {
		return ent;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @return the verifyinfo
	 */
	public String getVerifyinfo() {
		return verifyinfo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @return the fnasnum
	 */
	public long getFnasnum() {
		return fnasnum;
	}

	/**
	 * @return the idolnum
	 */
	public long getIdolnum() {
		return idolnum;
	}

	/**
	 * @return the tweetnum
	 */
	public long getTweetnum() {
		return tweetnum;
	}

}
