package com.sooncode.verification.service.intercepter;

import java.util.LinkedList;
import java.util.List;

import com.sooncode.verification.moduler.VerificationResult;

public class VerificationIntercepterChain  implements VerificationIntercepter{

	private List<VerificationIntercepter> verificationIntercepter = new LinkedList<>();
    
	private int index = 0;
	
	public VerificationIntercepterChain add(VerificationIntercepter vi){
		verificationIntercepter.add(vi);
		return this;
	}

	@Override
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter nextChain) {
        if(index >= verificationIntercepter.size()){
        	return null;
        }
		
		VerificationIntercepter vi = verificationIntercepter.get(index);
		index ++;
		return vi.doIntercepter(ve, nextChain);
		 
	}
}
