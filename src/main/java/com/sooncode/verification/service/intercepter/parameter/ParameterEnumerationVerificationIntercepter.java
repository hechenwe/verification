package com.sooncode.verification.service.intercepter.parameter;

import java.util.Arrays;
import java.util.List;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterEnumerationVerificationIntercepter implements ParameterVerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p, ParameterVerificationIntercepter pviChain) {
		VerificationResult vr = new VerificationResult();
		String enumeration = p.getEnumeration();
		if (enumeration != null && value != null) {
			String[] strs = p.getEnumeration().split(";");
			if (strs.length > 0) {
				List<String> values = Arrays.asList(strs);
				if (!values.contains(value)) {
					vr.setIsPass(false);
					vr.setReason("参数[" + key + "]值不属于指定的枚举值");
					return vr;
				}
			}
		}

		return pviChain.doIntercepter(key, value, p, pviChain);
	}

}