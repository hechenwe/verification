package com.sooncode.verification.service.intercepter;

import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.VerificationResult;

public class VerificationService {

	public VerificationResult verification(String jsonData, Method method, String requestMothod) {

		VerificationElement ve = new VerificationElement();
		ve.setJsonData(jsonData);
		ve.setMethod(method);
		ve.setTemporary(requestMothod);
		VerificationIntercepterChain vic = new VerificationIntercepterChain();
		RequestMethodVerificationIntercepter rmvi = new RequestMethodVerificationIntercepter();
		JsonFormatVerificationIntercepter jfvi = new JsonFormatVerificationIntercepter();
		vic.add(rmvi).add(jfvi);
		VerificationResult vr = vic.doIntercepter(ve, vic);
		if (vr == null) {
			vr = new VerificationResult();
			vr.setIsPass(true);
			vr.setReason("参数正常");
		}
		return vr;

	}

	public static void main(String[] args) {
		String jsonData = "{\"id\":23}";
		Method method = new Method();
		method.setMethod("POST");

		VerificationService vs = new VerificationService();
		VerificationResult vr = vs.verification(jsonData, method, "POST");

		System.out.println("vr " + vr.getIsPass() + " : " + vr.getReason());

	}
}
