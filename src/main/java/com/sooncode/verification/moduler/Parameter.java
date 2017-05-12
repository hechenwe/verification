package com.sooncode.verification.moduler;
 

/**
 * 参数 模型
 * @author pc
 *
 */
 public class Parameter {
	/**参数 KEY*/
	private String key ;
	
	/**参数验证类型*/
	private String type ;
	
	/**参数值 最大长度*/
	private Integer maxLength;
	
	/**是否必须*/
	private Boolean must;
	
	/**值域*/
	private String enumeration;

	public Parameter(){
		
	}
	
	 
	 
	public Boolean getMust() {
		return must;
	}
 

	public void setMust(Boolean must) {
		this.must = must;
	}
 

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

 

	public Integer getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}





	public String getEnumeration() {
		return enumeration;
	}





	public void setEnumeration(String enumeration) {
		this.enumeration = enumeration;
	}





	@Override
	public String toString() {
		return "{\"key\":\"" + key + "\",\"type\":\"" + type + "\",\"maxLength\":\"" + maxLength + "\",\"enumeration\":\"" + enumeration + "\"} must: "+must;
	}
 

	


	 
	
}