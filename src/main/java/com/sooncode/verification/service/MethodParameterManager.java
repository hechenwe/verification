package com.sooncode.verification.service;

import java.util.Hashtable;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sooncode.verification.moduler.Method;

/**
 * 加载参数配置文件
 * 
 * @author pc
 *
 */
public class MethodParameterManager {
	public static Map<String, Method> controllerMap = new Hashtable<>();
 
	public static Method getMethod(HttpServletRequest request) {
		String url = request.getRequestURL().toString();

		String[] strs = url.split("/");
		url = strs[strs.length - 2] + "/" + strs[strs.length - 1];

		return controllerMap.get(url);
	}
}
