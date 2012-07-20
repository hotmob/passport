package com.ammob.passport.enumerate;

public enum AttributeEnum {
	/** 用户账户 **/
	USER_USERNAME 				("username", "用户账户"),
	/** 用户密码 **/
	USER_PASSWORD 				("password", "用户密码"),
	/** 用户邮箱 **/
	USER_EMAIL 						("email", "用户密码"),
	/** 用户权限 **/
	USER_AUTHORITIES 			("authorities", "用户权限");
	
	private String value;
	private String description;
	private AttributeEnum(String value, String description){
		this.value = value;
		this.description = description;
	}
	public String getValue() {
		return value;
	}
	public String getDescription() {
		return description;
	}
}
