package com.sooncode.verification.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sooncode.verification.service.VerificationService;



public class DataInRequest {

	
	public Map<String,Object> getMap (HttpServletRequest request){
		
		String jsonData = (String) request.getAttribute(VerificationService.ATTRIBUTE_KEY);
		JSONObject jsonObj =	JSONObject.parseObject(jsonData);
		Map<String,Object> map = jsonObj;
		return map;
		 
	}
	
	
	public <M> M getModel (HttpServletRequest request,Class<M> mClass){
		String jsonData = (String) request.getAttribute(VerificationService.ATTRIBUTE_KEY);
		M m =JSON.parseObject(jsonData, mClass);
		return m;
	}
}
