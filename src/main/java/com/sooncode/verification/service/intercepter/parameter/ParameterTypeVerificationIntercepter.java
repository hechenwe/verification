package com.sooncode.verification.service.intercepter.parameter;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.RegexService;

public class ParameterTypeVerificationIntercepter implements ParameterVerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p, ParameterVerificationIntercepterChainI thisChain) {
		VerificationResult vr = new VerificationResult();
		 
        if(value != null){
        	Boolean  dataTypeError =  ! RegexService.verification(value.toString(), p.getType());
        	if (dataTypeError) {
        		vr.setIsPass(false);
        		vr.setReason("参数[" + key + "]的数据类型错误");
        	 return vr;
        	}  
        }
		return thisChain.doIntercepter(key, value, p);
	}

}