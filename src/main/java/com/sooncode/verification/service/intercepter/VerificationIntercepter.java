package com.sooncode.verification.service.intercepter;

import com.sooncode.verification.moduler.VerificationResult;

public interface VerificationIntercepter {
	public VerificationResult doIntercepter(VerificationElement ve, VerificationIntercepter nextChain);
}
