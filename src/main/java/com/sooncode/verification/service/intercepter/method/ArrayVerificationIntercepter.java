package com.sooncode.verification.service.intercepter.method;

import java.util.List;

 
import com.sooncode.verification.moduler.Array;
import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;
import com.sooncode.verification.service.intercepter.parameter.ParameterEnumerationVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterExistVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterLengthVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterTypeVerificationIntercepter;
import com.sooncode.verification.service.intercepter.parameter.ParameterVerificationIntercepterChain;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ArrayVerificationIntercepter implements VerificationIntercepter {
 
	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter nextChain) {

		String jsonData = ve.getJsonData();
		JSONObject jsonRoot = JSONObject.fromObject(jsonData);
		List<Array> arrays = ve.getMethod().getArrays();
		
		for (Array array : arrays) {
			String arrayKey = array.getKey();
			Object arrayObject =  jsonRoot.get(arrayKey);
			
			VerificationResult vr = new VerificationResult();
			if (arrayObject == null) {
				vr.setIsPass(false);
				vr.setReason("缺少[" + array.getKey() + "]数组");
				return vr;
			}
			
			JSONArray jsonArray =  JSONArray.fromObject(arrayObject);// jsonRoot.get(array.getKey());
			List<Parameter> parameters = array.getParameters();
		 
			for (Parameter p : parameters) {
				String key = p.getKey();
				 
				Object value = this.getObject2JsonArray(jsonArray,key);
				
				ParameterVerificationIntercepterChain pviChain = new ParameterVerificationIntercepterChain();
				pviChain.add(ParameterExistVerificationIntercepter.class)
				        .add(ParameterLengthVerificationIntercepter.class)
				        .add(ParameterTypeVerificationIntercepter.class)
				        .add(ParameterEnumerationVerificationIntercepter.class);
				
				VerificationResult thisVR  = pviChain.doIntercepter(key, value, p, pviChain);
			 
				if(thisVR == null || thisVR.getIsPass()){
					continue;
				}else{
					return thisVR;
				}
			}
			 
		}
		 
		return nextChain.doIntercepter(ve, nextChain);
	}
	
	private Object getObject2JsonArray(JSONArray jsonArray,String key){
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = (JSONObject) jsonArray.get(i);
			Object value = obj.get(key);
			if(value!=null){
				return value;
			}
		}
		
		return null;
	}
	
	

}
