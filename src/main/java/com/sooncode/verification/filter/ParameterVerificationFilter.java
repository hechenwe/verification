package com.sooncode.verification.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

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

	public void setConfLocation(String confLocation) throws Throwable {
		// Resource fileRource = new ClassPathResource(confLocation);

		// File file = fileRource.getFile();
		// logger.debug("[parameter_verification] path: " + file.getPath());

		// File file = ResourceUtils.getFile(confLocation);

		// String fileNames[];
		// fileNames = file.list();

		// for (int i = 0; i < fileNames.length; i++) {
		// File f = ResourceUtils.getFile(confLocation + File.separatorChar +
		// fileNames[i]);
		// new MethodParameter(f);
		// logger.debug("[parameter_verification] load..." + fileNames[i]);
		// }

		List<BufferedReader> list = getBufferedReaders(confLocation);

		for (BufferedReader br : list) {
			new MethodParameter(br);
		}

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain, VerificationFailure verificationFailure) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		ServletRequest servletRequest = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
		String jsonData = HttpServletStream.getString(servletRequest);
		String url = httpServletRequest.getRequestURI();
		url = getUrl(url);
		Method method = MethodParameterManager.getMethod(url);
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
			 * rd.getRespondHeadMap(); JSONObject js = JSONObject.fromObject(map);
			 * HttpServletStream.putString(js.toString(), (HttpServletResponse)response);
			 */
			verificationFailure.callBack(vr, response);
		}
	}

	private String getUrl(String url) {
		String[] strs = url.split("/");
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for (int i = 0; i < strs.length; i++) {
			if (!strs[i].equals("")) {
				sb.append(n == 0 ? strs[i] : "/" + strs[i]);
				n++;
			}
		}
		return sb.toString();
	}

	public static List<BufferedReader> getBufferedReaders(String confLocation) {

		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(confLocation);
			List<BufferedReader> list = new ArrayList<>();
			for (Resource resource : resources) {
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
				list.add(br);
			}
			return list;

		} catch (Exception e) {

			return null;

		}

	}

}
