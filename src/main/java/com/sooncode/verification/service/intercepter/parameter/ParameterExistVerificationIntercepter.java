package com.sooncode.verification.service.intercepter.parameter;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterExistVerificationIntercepter implements ParameterVerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p, ParameterVerificationIntercepter pviChain) {
		VerificationResult vr = new VerificationResult();
		boolean parameterIsMust = (p.getMust() == null || p.getMust() == true);
		if (parameterIsMust) {
			if (value == null) { // 没有参数
				vr.setIsPass(false);
				vr.setReason("缺少[" + key + "]参数");
				return vr;
			}
		}
		
		return pviChain.doIntercepter(key, value, p, pviChain);
	}

}