package com.johnson.core.mvc.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.johnson.core.mvc.view.utils.ResponseData;
import com.johnson.core.utils.JsonUtils;

public class JsonData implements IViewModal {

	private ResponseData data;

	public JsonData(Object data) {
		this.data = new ResponseData(HttpServletResponse.SC_OK, data);
	}

	public JsonData(int status, String message) {
		this.data = new ResponseData();
		this.data.setStatus(status);
		this.data.setMessage(message);
	}

	public ResponseData getResponseData() {
		return data;
	}

	@Override
	public void toClient(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ResponseData data = getResponseData();
		if (data != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String json = JsonUtils.toJson(data);
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}

	@Override
	public String toString() {
		return "Data [data=" + JsonUtils.toJson(data) + "]";
	}
}
