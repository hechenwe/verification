package com.sooncode.verification.service.intercepter;

import com.sooncode.verification.moduler.Method;

public class VerificationElement {
 
	private String jsonData;
	
	private Method method;
	
	private Object temporary;

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getTemporary() {
		return temporary;
	}

	public void setTemporary(Object temporary) {
		this.temporary = temporary;
	}
	
	
}
