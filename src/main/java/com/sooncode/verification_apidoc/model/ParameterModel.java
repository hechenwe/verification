package com.sooncode.verification_apidoc.model;

import java.util.List;

/**
 *
 * @author hechen
 * 
 */
public class ParameterModel {

	/** 参数名称 */
	private String parameterName;
	/** 最小长度 */
	private Integer minLength;
	/** 是否必须 */
	private String isMust;
	/** 参数示例*/
	private String parameterExample;
	/** 参数代码 */
	private String parameterCode;
	/** 最大长度 */
	private Integer maxLength;
	/** 参数数据类型 */
	private String parameterDataType;
	
	/** 参数数据类型 */
	private String interfacId;
	
	 

	private List<ParameterConstraintModel> parameterConstraintModels;

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}

	public String getParameterExample() {
		return parameterExample;
	}

	public void setParameterExample(String parameterExample) {
		this.parameterExample = parameterExample;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getParameterDataType() {
		return parameterDataType;
	}

	public void setParameterDataType(String parameterDataType) {
		this.parameterDataType = parameterDataType;
	}

	public List<ParameterConstraintModel> getParameterConstraintModels() {
		return parameterConstraintModels;
	}

	public void setParameterConstraintModels(List<ParameterConstraintModel> parameterConstraintModels) {
		this.parameterConstraintModels = parameterConstraintModels;
	}

	public String getInterfacId() {
		return interfacId;
	}

	public void setInterfacId(String interfacId) {
		this.interfacId = interfacId;
	}

	
}
