package com.sooncode.verification.service.intercepter;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterVerificationIntercepter implements VerificationIntercepter  {

	 
	public VerificationResult doIntercepter(String jsonDataString, String state, Method method) {
		JSONObject jsonRoot = JSONObject.parseObject(jsonDataString); 
		List<Parameter> parameters = method.getParameters();
		VerificationResult vr = new VerificationResult();
		for (Parameter p : parameters) {
					String key = p.getKey();
					if(p.getMust() == null || p.getMust() == true){
						if (jsonRoot.get(key) == null  ) {
							vr.setIsPass(false);
							vr.setReason("缺少[" + key + "]参数");
							return vr;
						}
					}
					Object val = jsonRoot.get(key);
					 
					 
				 
			}
		 	
		 return null;
	 
	}

	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter nextChain) {
		 
		String jsonData = ve.getJsonData();
		
		List<Parameter> parameters = ve.getMethod().getParameters();
		
		 for (Parameter p : parameters) {
			
		}
		
		
		
		return null;
	} 

}
