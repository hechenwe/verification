package com.sooncode.verification_apidoc.model;

import java.util.List;

public class InterfacModel {

	private String interfacId;
	/** 接口名称 */
	private String interfacName;
	/** JSON格式的参数 */
	private String jsonParameters;

	/** 返回数据类型 */
	private String returnDataType;
	/** 接口链接 */
	private String url;

	/** 接口代码 */
	private String interfacCode;

	/** 参数格式 */
	private String parameterFormat;

	/** 请求方式 */
	private String requestType;

	private String moduleId;

	private List<ParameterModel> parameterModels;
	private List<ArrayModel> arrayModels;

	public String getInterfacId() {
		return interfacId;
	}

	public void setInterfacId(String interfacId) {
		this.interfacId = interfacId;
	}

	public String getInterfacName() {
		return interfacName;
	}

	public void setInterfacName(String interfacName) {
		this.interfacName = interfacName;
	}

	public String getJsonParameters() {
		return jsonParameters;
	}

	public void setJsonParameters(String jsonParameters) {
		this.jsonParameters = jsonParameters;
	}

	public String getReturnDataType() {
		return returnDataType;
	}

	public void setReturnDataType(String returnDataType) {
		this.returnDataType = returnDataType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterfacCode() {
		return interfacCode;
	}

	public void setInterfacCode(String interfacCode) {
		this.interfacCode = interfacCode;
	}

	public String getParameterFormat() {
		return parameterFormat;
	}

	public void setParameterFormat(String parameterFormat) {
		this.parameterFormat = parameterFormat;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public List<ParameterModel> getParameterModels() {
		return parameterModels;
	}

	public void setParameterModels(List<ParameterModel> parameterModels) {
		this.parameterModels = parameterModels;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public List<ArrayModel> getArrayModels() {
		return arrayModels;
	}

	public void setArrayModels(List<ArrayModel> arrayModels) {
		this.arrayModels = arrayModels;
	}

}
