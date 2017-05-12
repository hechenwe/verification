package com.sooncode.verification.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

 

/**
 * 正则验证  服务
 * @author pc
 *
 */
public class RegexService {
	public static boolean verification (String value,String type){
		if(type.equals("STRING")){
			return true;
		}
		
		PropertiesUtil pu = new PropertiesUtil(PathUtil.getClassPath()+"regex.properties");
		String regex = pu.getString(type);
		Pattern pattern = Pattern.compile(regex);  
		Matcher matcher = pattern.matcher(value);  
	    return matcher.matches();
	}
	
	 
}
