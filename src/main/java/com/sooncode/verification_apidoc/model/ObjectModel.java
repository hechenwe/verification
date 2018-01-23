package com.sooncode.verification_apidoc.model;

import java.util.List;

public class ObjectModel {
    
	private String chineseAnnotation;
	private String key;
	private String isMust;
	private List<ParameterModel> parameterModels;
	private List<ArrayModel> arrayModels;
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
	public List<ArrayModel> getArrayModels() {
		return arrayModels;
	}
	public void setArrayModels(List<ArrayModel> arrayModels) {
		this.arrayModels = arrayModels;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	
	
	
	
}
