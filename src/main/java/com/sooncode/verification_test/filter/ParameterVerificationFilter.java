package com.sooncode.verification_test.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.sooncode.verification.filter.BodyReaderHttpServletRequestWrapper;
import com.sooncode.verification.filter.HttpServletStream;
import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.response.RespondHead;
import com.sooncode.verification.service.MethodParameterManager;
import com.sooncode.verification.service.VerificationService;

import net.sf.json.JSONObject;

public class ParameterVerificationFilter implements Filter {
	 

	 

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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		ServletRequest servletRequest = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
		String jsonData = HttpServletStream.getString(servletRequest);
		String url = httpServletRequest.getRequestURI();
		url = getUrl(url);
		Method method = MethodParameterManager.getMethod(url);
		if (method == null) {
			filterChain.doFilter(servletRequest, response);
			return;
		}

		VerificationResult vr = VerificationService.verification(jsonData, method, httpServletRequest.getMethod());

		if (vr.getIsPass()) {
			filterChain.doFilter(servletRequest, response);
		} else {

			Map<String, Object> map = new HashMap<>();
			RespondHead rd = new RespondHead();
			rd.setFailureMessage(vr.getReason());
			map = rd.getRespondHeadMap();
			JSONObject js = JSONObject.fromObject(map);
			HttpServletStream.putString(js.toString(), (HttpServletResponse) response);

		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
