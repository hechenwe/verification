package com.sooncode.verification.service.intercepter.method;

import com.alibaba.fastjson.JSONObject;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;

public class JsonFormatVerificationIntercepter implements VerificationIntercepter {

	private static final String JSON_FORMAT_ERROR = "JSON数据格式错误,或数据类型错误";

	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter vi) {
		String jsonDataString = ve.getJsonData();//

		VerificationResult vr = new VerificationResult();

		if (this.parseObject4Json(jsonDataString) == false) {
			vr.setIsPass(false);
			vr.setReason(JSON_FORMAT_ERROR);
			return vr;
		}

		return vi.doIntercepter(ve, vi);

	}

	private boolean parseObject4Json(String jsonDataString) {
		try {
			JSONObject.parseObject(jsonDataString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
