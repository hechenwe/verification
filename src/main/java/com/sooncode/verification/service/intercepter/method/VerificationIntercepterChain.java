package com.sooncode.verification.service.intercepter.method;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;

public class VerificationIntercepterChain  implements VerificationIntercepter{

	private List<VerificationIntercepter> verificationIntercepter = new LinkedList<>();
    
	private int index = 0;
	 
	
	public  VerificationIntercepterChain add(Class< ? extends VerificationIntercepter > clas){
		try {
			VerificationIntercepter vi = clas.newInstance();
			verificationIntercepter.add(vi);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter nextChain) {
        if(index >= verificationIntercepter.size()){
        	VerificationResult vr = new VerificationResult();
        	vr.setIsPass(true);
        	vr.setReason("通过验证");
        	return vr;
        }
		
		VerificationIntercepter vi = verificationIntercepter.get(index);
		index ++;
		return vi.doIntercepter(ve, nextChain);
		 
	}
}
