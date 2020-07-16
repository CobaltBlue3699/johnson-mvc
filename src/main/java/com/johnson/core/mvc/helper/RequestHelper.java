package com.johnson.core.mvc.helper;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.johnson.core.mvc.bean.Param;

public final class RequestHelper {

	public static Param<String> createParam(HttpServletRequest request) throws IOException {
		Param<String> paramMap = new Param<>();
		Enumeration<String> paramNames = request.getParameterNames();
		if (!paramNames.hasMoreElements()) {
			return null;
		}

		while (paramNames.hasMoreElements()) {
			String fieldName = paramNames.nextElement();
			String fieldValue = request.getParameter(fieldName);
			paramMap.put(fieldName, fieldValue);
		}

		return paramMap;
	}

}
