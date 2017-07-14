package com.sooncode.verification.service.intercepter.parameter;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterLengthVerificationIntercepter implements ParameterVerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p, ParameterVerificationIntercepterChainI thisChain) {
		VerificationResult vr = new VerificationResult();
		  
			if (value != null && p !=null ) { // 没有参数
				int length = value.toString().length();
				
				if(length > p.getMaxLength()){
					vr.setIsPass(false);
					vr.setReason("参数[" + key + "]值太长");
					return vr;
					
				} 
				
				int minLength = p.getMinLength() == null ? 1 : p.getMinLength();
				if(length < minLength){
					vr.setIsPass(false);
					vr.setReason("参数[" + key + "]值太短");
					return vr;
				}
				
			}
		 
		
		return thisChain.doIntercepter(key, value, p);
	}

}