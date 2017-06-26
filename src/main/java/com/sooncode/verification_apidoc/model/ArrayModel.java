package com.sooncode.verification_apidoc.model;

import java.util.List;

public class ArrayModel {
    
	private String chineseAnnotation;
	private String key;
	private List<ParameterModel> parameterModels;
	public String getChineseAnnotation() {
		return chineseAnnotation;
	}
	public void setChineseAnnotation(String chineseAnnotation) {
		this.chineseAnnotation = chineseAnnotation;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<ParameterModel> getParameterModels() {
		return parameterModels;
	}
	public void setParameterModels(List<ParameterModel> parameterModels) {
		this.parameterModels = parameterModels;
	}
	
	
}
