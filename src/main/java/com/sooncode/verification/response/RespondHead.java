package com.sooncode.verification.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class RespondHead {
	  private Map<String,Object> map= new LinkedHashMap<>();
	  
	  private static final String PARAMETER_CHECK_CODE = "parameterCheckCode";
	  
	  private static final String SUCCESS = "SUCCESS";
	  private static final String SUCCESS_CHINESE = "成功";
	  private static final String PARAMETER_CHECK_MESSAGE = "parameterCheckMessage";
	  private static final String FAILURE = "FAILURE";
	  public RespondHead(){
		  map.put(PARAMETER_CHECK_CODE,SUCCESS);
		  map.put(PARAMETER_CHECK_MESSAGE,SUCCESS_CHINESE);
	  }
	  
	 
	  public void setFailureMessage(String value){
		  map.put(PARAMETER_CHECK_CODE,FAILURE);
		  map.put(PARAMETER_CHECK_MESSAGE,value);
	  }
	  
	  
	  public Map<String,Object> getRespondHeadMap(){
		  return this.map;
	  }
	  
	 
}
