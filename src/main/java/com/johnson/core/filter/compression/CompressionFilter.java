package com.johnson.core.filter.compression;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CompressionFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String encodings = req.getHeader("Accept-Encoding");
		if (encodings != null && encodings.contains("gzip")) {
			CompressionResponseWrapper responseWrapper = new CompressionResponseWrapper(resp);
			responseWrapper.setHeader("Content-Encoding", "gzip");

			chain.doFilter(request, responseWrapper);

			responseWrapper.finish();
		} else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
	}
}