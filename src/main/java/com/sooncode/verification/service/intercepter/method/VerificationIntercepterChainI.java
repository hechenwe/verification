package com.sooncode.verification.service.intercepter.method;

import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.intercepter.VerificationElement;

public interface VerificationIntercepterChainI {  
	public VerificationResult doIntercepter(VerificationElement ve) ;
         
}
