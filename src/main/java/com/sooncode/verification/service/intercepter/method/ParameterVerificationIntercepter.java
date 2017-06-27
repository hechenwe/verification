package com.sooncode.verification.service.intercepter.method;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;
import com.sooncode.verification.service.intercepter.VerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterEnumerationVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterExistVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterLengthVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterTypeVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterVerificationIntercepterChain;

public class ParameterVerificationIntercepter implements VerificationIntercepter {
 
	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter nextChain) {

		String jsonData = ve.getJsonData();
		JSONObject jsonRoot = JSONObject.parseObject(jsonData);
		List<Parameter> parameters = ve.getMethod().getParameters();
		
		for (Parameter p : parameters) {
			String key = p.getKey();
			Object value = jsonRoot.get(key);
			ParameterVerificationIntercepterChain pviChain = new ParameterVerificationIntercepterChain();
			pviChain.add(ParameterExistVerificationIntercepter.class)
			.add(ParameterLengthVerificationIntercepter.class)
			.add(ParameterTypeVerificationIntercepter.class)
			.add(ParameterEnumerationVerificationIntercepter.class);
			
			VerificationResult thisVR  = pviChain.doIntercepter(key, value, p, pviChain);
			if(thisVR == null || thisVR.getIsPass()){
				continue;
			}else{
				return thisVR;
			}
			
		}
		
		return nextChain.doIntercepter(ve, nextChain);
		 
	}

}
