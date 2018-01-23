package com.sooncode.verification.service.intercepter.method;

import java.util.List;

import com.sooncode.verification.moduler.Array;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArrayVerificationIntercepter implements VerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepterChainI thisChain) {

		String jsonData = ve.getJsonData();
		JSONObject jsonRoot = JSONObject.fromObject(jsonData);
		List<Array> arrays = ve.getMethod().getArrays();

		for (Array array : arrays) {
			String arrayKey = array.getKey();
			Object arrayObject = jsonRoot.get(arrayKey);

			VerificationResult vr = new VerificationResult();
			if (arrayObject == null) {
				vr.setIsPass(false);
				vr.setReason("缺少[" + array.getKey() + "]数组");
				return vr;
			}

			JSONArray jsonArray = JSONArray.fromObject(arrayObject);// jsonRoot.get(array.getKey());
			List<Parameter> parameters = array.getParameters();

			for (Parameter p : parameters) {
				String key = p.getKey();
				Object value = this.getObject2JsonArray(jsonArray, key);
				VerificationResult thisVR = ParameterVerificationIntercepter.verificationParameter(p, value);
				if (thisVR == null || thisVR.getIsPass()) {
					continue;
				} else {
					return thisVR;
				}
			}

		}

		return thisChain.doIntercepter(ve);
	}

	private Object getObject2JsonArray(JSONArray jsonArray, String key) {
		Object value = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			if(isJsonObject(object)) {
				JSONObject obj = (JSONObject) object;
				value = obj.get(key);
				if (value != null) {
					break;
				}
				
			}
		}
		return value;
	}
	
	private boolean isJsonObject(Object jsonData) {
		try {
			JSONObject.fromObject(jsonData.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
