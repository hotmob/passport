package com.ammob.passport.enumerate;

public enum StateEnum {

	/** 301 邮件地址已存在  **/
	EMAIL_EXISTENCE 				(301, "电子邮件已存在"),
	/** 302 邮件地址不存在  **/
	EMAIL_NOT_EXISTENCE		(302, "电子邮件不存在"),
	/** 303 电子邮件发送失败  **/
	EMAIL_SEND_SUCESS			(303, "电子邮件发送失败"),
	/** 304电子邮件发送成功  **/
	EMAIL_SEND_FAIL 				(304, "电子邮件发送成功"),
	/** 305 电子邮件已通过验证  **/
	EMAIL_VERIFIED   				(305, "电子邮件已通过验证"),
	/** 306 电子邮件未通过验证  **/
	EMAIL_NOT_VERIFIED   		(306, "电子邮件未通过验证"),


	/** 201  用户名已存在 **/
	USERNAME_EXISTENCE    			(201, "用户名已存在"),
	/** 202  用户名不存在 **/
	USERNAME_NOT_EXISTENCE    	(202, "用户名不存在"),
	
	/** 210  用户授权成功 **/
	USER_AUTHORIZED_SUCESS	   	(210, "用户授权成功"),
	/** 211  用户授权失败 **/
	USER_AUTHORIZED_FAIL    			(211, "用户授权失败"),
	
	/** 100 函数执行成功  **/
	SUCESS											(100, "函数执行成功"),
	
	/** 0      函数状态未知  **/
	UNKNOWN									(0, "函数状态未知"),
	
	/** -1      重复刷新  **/
	WARN_REPEAT				   				(-1, "重复刷新"),
	
	/** -100  函数发生错误  **/
	ERROR					   						(-100, "函数发生错误"),
	
	/** -250  用户错误,注册错误,用户注册失败,包含格式错误  **/
	ERROR_USER_REGISTER           				(-250, "注册错误,用户注册失败,包含格式错误"),
	/** -251  用户错误,注册错误,用户名验证失败,包含格式错误  **/
	ERROR_USER_REGISTER_UESRNAME  		(-251, "注册错误,用户名验证失败,包含格式错误"),
	/** -252  用户错误,注册错误,密码效验失败,包含格式错误  **/
	ERROR_USER_REGISTER_PASSWORD  		(-252, "注册错误,密码效验失败,包含格式错误"),
	/** -253  用户错误,注册错误,电子邮箱效验失败,包含格式错误  **/
	ERROR_USER_REGISTER_EMAIL     			(-253, "注册错误,电子邮箱效验失败,包含格式错误"),
	/** -254  用户错误,注册错误,用户姓名效验失败,包含格式错误  **/
	ERROR_USER_REGISTER_GIVENNAME 		(-254, "注册错误,用户姓名效验失败,包含格式错误"),
	
	/** -260  用户错误,格式错误  **/
	ERROR_USER_FORMAT           				(-260, "用户格式错误"),
	/** -261  用户错误,格式错误,用户名格式错误  **/
	ERROR_USER_FORMAT_UESRNAME  		(-261, "用户名格式错误"),
	/** -262  用户错误,格式错误,密码格式错误  **/
	ERROR_USER_FORMAT_PASSWORD  		(-262, "密码格式错误"),
	/** -263  用户错误,格式错误,电子邮箱格式错误  **/
	ERROR_USER_FORMAT_EMAIL     			(-263, "电子邮箱格式错误"),
	/** -264  用户错误,格式错误,用户姓名格式错误  **/
	ERROR_USER_FORMAT_GIVENNAME 		(-264, "用户姓名格式错误"),
	
	/** -270  用户资料更新错误  **/
	ERROR_USER_UPDATA           			(-270, "用户资料更新错误"),
	/** -275  用户状态更新错误  **/
	ERROR_USER_UPDATA_EMAIL     	(-273, "用户邮箱更新错误"),	
	/** -275  用户状态更新错误  **/
	ERROR_USER_UPDATA_STATUS    	(-275, "用户状态更新错误"),
	
	/** -300  HTTPSQS队列错误  **/
	ERROR_HTTPSQS		(-300, "HTTPSQS队列错误"),
	
	/** -400  用户错误,方法错误,请求方法不存在  **/
	ERROR_METHOD		(-400, "用户错误,方法错误,请求方法不存在"),
	
	/** -500  格式错误,请求格式无法解析  **/
	ERROR_FORMAT		(-500, "格式错误,请求格式无法解析"),
	
	/** -600  格式错误,IO错误 **/
	ERROR_IO					(-600, "格式错误, IO错误");
	
	/**
	 * Code value
	 */
	private int value;

	/**
	 * State description
	 */
	private String description;
	
	/**
	 * construction
	 * @param value
	 *     String Code value
	 * @param description
	 *     String State description
	 */
	private StateEnum(int value, String description){
		this.value = value;
		this.description = description;
	}
	
	/**
	 * Get Code value
	 * @return
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Get State description
	 * @return
	 */
	public String getDescription() {
		return description;
	}
}
