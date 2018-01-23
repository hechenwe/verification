package com.sooncode.verification_test.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CrossFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse rs, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) rs;

		response.setHeader("Access-Control-Allow-Origin", "*");
		// Access-Control-Allow-Origin
		/**
		 * allowed-origins="*" allowed-methods="GET,POST,PUT,DELETE,HEAD,OPTIONS"
		 * allowed-headers="DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,accept,Content-Type,token,userId,userName"
		 * allow-credentials="true"
		 * exposed-headers="Content-Type,Set-Cookie,Date,Cache-Control,Accept"
		 * max-age="1800" />
		 */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,HEAD,OPTIONS");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,accept,Content-Type");
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
