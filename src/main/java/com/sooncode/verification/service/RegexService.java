package com.sooncode.verification.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.ResourceUtils;

import com.sooncode.verification.filter.ParameterVerificationFilter;

 

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
		try {
			File file =  ResourceUtils.getFile(ParameterVerificationFilter.regexConfLocation);
			String path = file.getPath();
			PropertiesUtil pu = new PropertiesUtil(path);
			String regex = pu.getString(type);
			Pattern pattern = Pattern.compile(regex);  
			Matcher matcher = pattern.matcher(value);  
			return matcher.matches();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	 
}
