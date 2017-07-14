package com.sooncode.verification.service;

 
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * HttpServlet 流
 * 
 * @author pc
 *
 */
public class HttpServletStream {

	 
	
	/**
	 * 将字符串写入response输入流中
	 * 
	 * @param jsonString
	 * @param response
	 */
	public static void putString(String string, HttpServletResponse response) {
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
