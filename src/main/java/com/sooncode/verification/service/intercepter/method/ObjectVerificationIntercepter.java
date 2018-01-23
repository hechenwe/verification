package com.sooncode.verification.service.intercepter.method;

import java.util.List;

import com.sooncode.verification.moduler.Array;
import com.sooncode.verification.moduler.JsonObject;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 验证jsonObject
 * 
 * @author hechenwe@gmail.com
 *
 */
public class ObjectVerificationIntercepter implements VerificationIntercepter {

	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepterChainI thisChain) {

		String jsonData = ve.getJsonData();
		JSONObject jsonRoot = JSONObject.fromObject(jsonData);

		List<JsonObject> jsonObjects = ve.getMethod().getJsonObjects();
		for (JsonObject jo : jsonObjects) {
			String jsonObjectKey = jo.getKey();
			Object jsonObjectData = jsonRoot.get(jsonObjectKey);
			JSONObject jsonO = (JSONObject) jsonObjectData;
			VerificationResult vr = new VerificationResult();
			if (jo.getMust()) { // 参数必须提供
				if (jsonObjectData == null) {
					vr.setIsPass(false);
					vr.setReason("缺少[" + jsonObjectKey + "]对象");
					return vr;
				}
			} else { // 参数为 可选

				if (jsonObjectData != null) {
					List<Parameter> parameters = jo.getParameters();
					for (Parameter p : parameters) {
						String key = p.getKey();
						Object value = jsonO.get(key);
						VerificationResult thisVR = ParameterVerificationIntercepter.verificationParameter(p, value);
						if (thisVR == null || thisVR.getIsPass()) {
							continue;
						} else {
							return thisVR;
						}
					}

					List<Array> arrays = jo.getArrays();

					for (Array array : arrays) {
						String arrayKey = array.getKey();
						Object arrayObject = jsonO.get(arrayKey);

						VerificationResult vr2 = new VerificationResult();
						if (array.getMust()) {
							if (arrayObject == null) {
								vr2.setIsPass(false);
								vr2.setReason("缺少[" + array.getKey() + "]数组");
								return vr2;
							}
						} else {

							if (arrayObject != null) {

								JSONArray jsonArray = JSONArray.fromObject(arrayObject);// jsonRoot.get(array.getKey());
								List<Parameter> parameters2 = array.getParameters();

								for (Parameter p : parameters2) {
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

						}

					}

				}

			}

		}

		return thisChain.doIntercepter(ve);
	}

	private Object getObject2JsonArray(JSONArray jsonArray, String key) {
		Object value = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			value = obj.get(key);
			if (value != null) {
				break;
			}
		}
		return value;
	}

}
