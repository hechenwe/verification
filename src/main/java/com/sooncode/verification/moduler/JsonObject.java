package com.sooncode.verification.moduler;

import java.util.List;

/**
 * JSON格式 Object 模型
 * 
 * @author hechenwe@gmail.com
 *
 */
public class JsonObject  {
	/** 数组 KEY */
	private String key;

	private List<Parameter> parameters;
	
	private List<Array> arrays;
  
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
	
	

	public List<Array> getArrays() {
		return arrays;
	}


	public void setArrays(List<Array> arrays) {
		this.arrays = arrays;
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
