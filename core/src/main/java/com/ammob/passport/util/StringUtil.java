package com.ammob.passport.util;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils {

	/**
	 * 判断字符串是否是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
	    return Pattern.compile("^[-+]?[0-9]*$").matcher(str.trim()).matches();   
	}
	
	/**
	 * 判断字符串是否是字母数字组合,正负号匹配
	 * @param str
	 * @return
	 */
	public static boolean isCharacterAndNumeric(String str){
	    return Pattern.compile("^[-+]?[a-zA-Z0-9]*$").matcher(str.trim()).matches();   
	}
}
