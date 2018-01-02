package com.sooncode.verification.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;

import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.service.MethodParameter;
import com.sooncode.verification.service.MethodParameterManager;
import com.sooncode.verification.service.VerificationService;

public class ParameterVerificationFilter {
	protected final Log logger = LogFactory.getLog(getClass());
	public static String regexConfLocation;

	public void setRegexConfLocation(String regexLocation) {
		regexConfLocation = regexLocation;
	}

	public void setConfLocation(String confLocation) throws FileNotFoundException {

		File file = ResourceUtils.getFile(confLocation);
		String fileNames[];
		fileNames = file.list();

		for (int i = 0; i < fileNames.length; i++) {
			File f = ResourceUtils.getFile(confLocation + File.separatorChar + fileNames[i]);
			new MethodParameter(f);
			logger.debug("[parameter_verification] 加载完成" + fileNames[i] + "参数配置文件");
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain, VerificationFailure verificationFailure) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		ServletRequest servletRequest = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
		String jsonData = HttpServletStream.getString(servletRequest);

		Method method = MethodParameterManager.getMethod(httpServletRequest.getRequestURI());
		if (method == null) {
			logger.warn("[parameter_verification] '" + httpServletRequest.getRequestURL() + "'接口 ,没有参数描述");
			filterChain.doFilter(servletRequest, response);
			return;
		}

		VerificationResult vr = VerificationService.verification(jsonData, method, httpServletRequest.getMethod());

		if (vr.getIsPass()) {
			filterChain.doFilter(servletRequest, response);
		} else {
			/*
			 * Map<String ,Object> map = new HashMap<>(); RespondHead rd = new
			 * RespondHead(); rd.setFailureMessage(vr.getReason()); map =
			 * rd.getRespondHeadMap(); JSONObject js =
			 * JSONObject.fromObject(map);
			 * HttpServletStream.putString(js.toString(),
			 * (HttpServletResponse)response);
			 */
			verificationFailure.callBack(vr, response);
		}
	}

}
