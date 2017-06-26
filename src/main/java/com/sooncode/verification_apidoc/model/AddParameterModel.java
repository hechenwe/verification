package com.sooncode.verification_apidoc.model;


 

/**
*
* @author hechen 
* 
*/ 
public class AddParameterModel  { 
	 
	 /** 参数编号 */ 
	 private String parameterId; 
	 
	 /** 参数名称 */
	 private String parameterName ;
	 
	 /** 参数代码 */
	 private String parameterCode ;
	 
	 /** 最小长度 */
	 private Integer minLength ;
	 
	 /** 最大长度 */
	 private Integer maxLength ;
	 
	 /** 是否必须 */
	 private String isMust ;
	 
	 /** 参数数据类型 */
	 private String parameterDataType ;
	 
	 /** 接口_编号 */
	 private String interfacId ;
	 
	 /** 参数实例 */
	 private String parameterExample ;

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getParameterDataType() {
		return parameterDataType;
	}

	public void setParameterDataType(String parameterDataType) {
		this.parameterDataType = parameterDataType;
	}

	public String getInterfacId() {
		return interfacId;
	}

	public void setInterfacId(String interfacId) {
		this.interfacId = interfacId;
	}

	public String getParameterExample() {
		return parameterExample;
	}

	public void setParameterExample(String parameterExample) {
		this.parameterExample = parameterExample;
	}
	

	 
}
