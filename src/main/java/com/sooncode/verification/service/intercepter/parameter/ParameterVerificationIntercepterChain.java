package com.sooncode.verification.service.intercepter.parameter;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.verification.moduler.Parameter;
import com.sooncode.verification.moduler.VerificationResult;

public class ParameterVerificationIntercepterChain  implements ParameterVerificationIntercepterChainI{

	
	private List<ParameterVerificationIntercepter> pvies = new LinkedList<>();
	
	private int index = 0;
	
	public ParameterVerificationIntercepterChain add(ParameterVerificationIntercepter pvi){
		pvies.add(pvi);
		return this;
	}
	
	public  ParameterVerificationIntercepterChain add(Class< ? extends ParameterVerificationIntercepter > clas){
		try {
			ParameterVerificationIntercepter pvi = clas.newInstance();
			pvies.add(pvi);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return this;
	}
	 
	@Override
	public VerificationResult doIntercepter(String key, Object value, Parameter p) {
		if(index >= pvies.size()){
			VerificationResult vr = new VerificationResult();
        	vr.setIsPass(true);
        	vr.setReason("通过验证");
        	return vr;
		}
		
		ParameterVerificationIntercepter pvi = pvies.get(this.index);
		index ++ ;
		
		return pvi.doIntercepter(key, value, p,this);
	}

}
