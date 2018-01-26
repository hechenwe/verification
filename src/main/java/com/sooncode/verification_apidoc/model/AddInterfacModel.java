package com.sooncode.verification_apidoc.model;



public class AddInterfacModel {
	 private String interfacId; 
	 /** 接口名称 */
	 private String interfacName ;
	 /** JSON格式的参数 */
	 private String jsonParameters ;
	 
	 /** 返回数据类型 */
	 private String returnDataType ;
	 /** 接口链接 */
	 private String url ;
	  
	 /** 接口代码 */
	 private String interfacCode ;
	 
	 /** 参数格式 */
	 private String parameterFormat ;
	 
	 
	 /** 请求方式 */
	 private String requestType ;
	 
	 
	 /** 模块_编号 */
	 private String moduleId ;
	 
	 private String interfacNumber;
	 
	 


	public String getInterfacNumber() {
		return interfacNumber;
	}


	public void setInterfacNumber(String interfacNumber) {
		this.interfacNumber = interfacNumber;
	}


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


	public String getModuleId() {
		return moduleId;
	}


	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	 
	  
}
