package com.sooncode.verification.moduler;

/**
 * 验证结果
 * 
 * @author pc
 *
 */
public class VerificationResult {
	/** 是否通过 */
	private Boolean isPass;

	/** 原因 */
	private String reason;

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
