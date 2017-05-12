package com.sooncode.verification.moduler;

import java.util.List;


/**
 * 接口 模型
 * 
 * @author pc
 *
 */
public class Method {
	/** 接口地址 */
	private String url;

	/** 请求方式 */
	private String method;
 
	/**
	 * JSON参数中的数组模型
	 */
	private List<Array> arrays;

	/** 接口的参数 */
	private List<Parameter> parameters;

	 

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	 

	public List<Array> getArrays() {
		return arrays;
	}

	public void setArrays(List<Array> arrays) {
		this.arrays = arrays;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "{\"url\":\"" + url + "\",\"method\":\"" + method + "\",\"arrays\":\"" + arrays + "\",\"parameters\":\"" + parameters + "\"}  ";
	}

}
