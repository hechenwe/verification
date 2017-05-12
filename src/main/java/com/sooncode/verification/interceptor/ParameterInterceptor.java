package com.sooncode.verification.interceptor;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sooncode.verification.moduler.Method;
import com.sooncode.verification.moduler.VerificationResult;
import com.sooncode.verification.response.RespondHead;
import com.sooncode.verification.service.MethodParameter;
import com.sooncode.verification.service.MethodParameterManager;
import com.sooncode.verification.service.VerificationService;

public class ParameterInterceptor extends HandlerInterceptorAdapter {
	
 
	 
	public void setConfLocation(String confLocation) throws FileNotFoundException{
		 
		  File file =  ResourceUtils.getFile(confLocation);
		  String fileNames[];
		  fileNames=file.list();
		  
		  for(int i=0;i<fileNames.length;i++)
		  {
		   File f =  ResourceUtils.getFile(confLocation+File.separatorChar+ fileNames[i]);
		   new MethodParameter(f);
		   System.out.println("[加载参数配置文件]："+fileNames[i]);
		  }
		
	} 
	
	
	 
	private static Logger logger = Logger.getLogger("ParameterInterceptor.class");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       
        Method i = MethodParameterManager.getMethod(request);
		if (i == null) {
			logger.warn("[" + request.getRequestURL() + "接口] 没有参数描述");
			return true;
		}
		VerificationResult vr = VerificationService.verificationInterface(request, i);
		if (vr.getIsPass()) {
			return true;
		} else {
			RespondHead rd = new RespondHead();
			rd.setFailureMessage(vr.getReason());
			Map<String ,Object> map = rd.getRespondHeadMap();
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			int index =0;
			for (Entry<String, Object> en : map.entrySet()) {
				String key = en.getKey();
				String val = (String) en.getValue();
				if(index==0){
				sb.append("\"" +key+ "\":\"" + val +"\",");
				}else{
				sb.append("\"" +key+ "\":\"" + val +"\"");
				}
				index++;
			}
			sb.append("}");
			putString(sb.toString(),response);
			return false;
		}
		 
	}

	/**
	 * 将字符串写入response输入流中
	 * 
	 * @param jsonString
	 * @param response
	 */
	private static void putString(String string, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.append(string);
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			printWriter.close();
		}
	}
	
}
