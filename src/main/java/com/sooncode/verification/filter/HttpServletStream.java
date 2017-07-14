package com.sooncode.verification.filter;

 
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * HttpServlet 流
 * 
 * @author pc
 *
 */
public class HttpServletStream {

	/**
	 * 读取字符流
	 * 
	 * @param is
	 * @param contentLen
	 * @return
	 */
	public static final String getString(ServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			 
			e1.printStackTrace();
		}
		int contentLen = request.getContentLength();
		if (contentLen > 0) {
			int readLen = 0;

			int readLengthThisTime = 0;

			byte[] message = new byte[contentLen];

			try {
				InputStream inputStream = request.getInputStream();
				while (readLen != contentLen) {

					readLengthThisTime = inputStream.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}

					readLen += readLengthThisTime;
				}
				 

				return new String(message);
			} catch (IOException e) {

			}
		}
		

		return null;
	}
	
	/**
	 * 将字符串写入response输入流中
	 * 
	 * @param jsonString
	 * @param response
	 */
	public static void putString(String string, ServletResponse response) {
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
