package com.sooncode.verification.service.intercepter.parameter;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterExistVerificationIntercepter implements ParameterVerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p, ParameterVerificationIntercepterChainI thisChain) {
		VerificationResult vr = new VerificationResult();
		boolean parameterIsMust = (p.getMust() == null || p.getMust() == true);
		if (parameterIsMust) {
			if (value == null) { // 没有参数
				vr.setIsPass(false);
				vr.setReason("缺少[" + key + "]参数");
				return vr;
			}
		}
		
		return thisChain.doIntercepter(key, value, p);
	}

}