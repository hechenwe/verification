package com.sooncode.verification.filter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.sooncode.verification.service.MethodParameter;

public class VerificationConfigLoad  {
	protected final Log logger = LogFactory.getLog(getClass());
	public static String regexConfLocation;

	public void setRegexConfLocation(String regexLocation) {
		regexConfLocation = regexLocation;
	}

	public void setConfLocation(String confLocation) throws Throwable {
		 

		List<BufferedReader> list = getBufferedReaders(confLocation);

		for (BufferedReader br : list) {
			new MethodParameter(br);
		}

	}
	 

	 

	public static List<BufferedReader> getBufferedReaders(String confLocation) {

		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(confLocation);
			List<BufferedReader> list = new ArrayList<>();
			for (Resource resource : resources) {
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
				list.add(br);
			}
			return list;

		} catch (Exception e) {

			return null;

		}

	}

	 

}
