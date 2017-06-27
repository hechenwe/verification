package com.sooncode.verification.service.intercepter.parameter;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public interface ParameterVerificationIntercepter {
	
	public VerificationResult doIntercepter(String key,Object value, Parameter p ,ParameterVerificationIntercepter pviChain);
	
}
