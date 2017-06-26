package com.sooncode.verification.service.intercepter;


import com.sooncode.verification.moduler.VerificationResult;

public class RequestMethodVerificationIntercepter implements VerificationIntercepter {

	private static final String REQUEST_METHOD_ERROR = "请求方式错误";

	@Override
	public VerificationResult doIntercepter(VerificationElement ve,VerificationIntercepter vi) {

		 
		VerificationResult vr = new VerificationResult();
		if (!ve.getTemporary() .equals(ve.getMethod().getMethod())) {
			vr.setIsPass(false);
			vr.setReason(REQUEST_METHOD_ERROR);
			return vr;
		}else{
			return vi.doIntercepter(ve, vi);
		}

	}
	 

}
