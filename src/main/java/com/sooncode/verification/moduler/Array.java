package com.sooncode.verification.moduler;

import java.util.List;

/**
 * JSON格式 数组 模型
 * 
 * @author pc
 *
 */
public class Array  {
	/** 数组 KEY */
	private String key;

	private List<Parameter> parameters;
  
	private Boolean must;


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public List<Parameter> getParameters() {
		return parameters;
	}


	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	
	
	public Boolean getMust() {
		return must;
	}


	public void setMust(Boolean must) {
		this.must = must;
	}


	@Override
	public String toString() {
		return "{\"key\":\"" + key + "\",\"parameters\":\"" + parameters + "\"}  ";
	}
 
	
}
