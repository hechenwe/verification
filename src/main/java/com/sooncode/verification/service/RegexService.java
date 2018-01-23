package com.sooncode.verification.service;

import java.io.BufferedReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sooncode.verification.filter.VerificationConfigLoad;

/**
 * 正则验证 服务
 * 
 * @author pc
 *
 */
public class RegexService {
	public static boolean verification(String value, String type) {
		if (type.equals("STRING")) {
			return true;
		}

		List<BufferedReader> list = VerificationConfigLoad.getBufferedReaders(VerificationConfigLoad.regexConfLocation);
		PropertiesUtil pu = new PropertiesUtil(list.get(0));
		String regex = pu.getString(type);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();

	}

}
