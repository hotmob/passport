package com.ammob.passport.util;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils {

	public static boolean isNumeric(String str){
	    Pattern pattern = Pattern.compile("^\\-?\\d+$");
	    return pattern.matcher(str).matches();   
	 }
}
