package com.sooncode.verification.service.intercepter.parameter;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterLengthVerificationIntercepter implements ParameterVerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p, ParameterVerificationIntercepter pviChain) {
		VerificationResult vr = new VerificationResult();
		  
			if (value != null ) { // 没有参数
				int length = value.toString().length();
				
				if(length > p.getMaxLength()){
					vr.setIsPass(false);
					vr.setReason("参数[" + key + "]值太长");
					return vr;
					
				} 
				
				if(length < p.getMinLength()){
					vr.setIsPass(false);
					vr.setReason("参数[" + key + "]值太短");
					return vr;
				}
				
			}
		 
		
		return pviChain.doIntercepter(key, value, p, pviChain);
	}

}