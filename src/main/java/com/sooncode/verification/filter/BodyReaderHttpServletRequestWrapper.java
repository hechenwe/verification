package com.sooncode.verification.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;

	public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		String data = HttpServletStream.getString(request);
		this.body = data.getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {

		final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);

		return new ServletInputStream() {

			public int read() {
				return bais.read();
			}

		};
	}
}
