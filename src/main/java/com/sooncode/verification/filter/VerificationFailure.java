package com.sooncode.verification.filter;

import javax.servlet.ServletResponse;

import com.sooncode.verification.moduler.VerificationResult;

public interface VerificationFailure {

	
	public void callBack(VerificationResult vr , ServletResponse response);
}
