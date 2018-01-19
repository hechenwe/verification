package com.sooncode.verification.service;

import java.util.Hashtable;

import java.util.Map;


import com.sooncode.verification.moduler.Method;

/**
 * 加载参数配置文件
 * 
 * @author pc
 *
 */
public class MethodParameterManager {
	public static Map<String, Method> controllerMap = new Hashtable<>();
 
	public static Method getMethod(String requestURL) {
		return controllerMap.get(requestURL);
	}
}
