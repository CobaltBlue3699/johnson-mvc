package com.johnson.core.mvc.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.johnson.core.mvc.helper.ConfigHelper;
import com.johnson.core.utils.JsonUtils;

public class View implements IViewModal {

	private String path;

	private Map<String, Object> model;

	public View(String path) {
		this.path = path;
		model = new HashMap<String, Object>();
	}

	public View addModel(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public String getPath() {
		return path;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	@Override
	public void toClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = getPath();
		if (StringUtils.isNotEmpty(path)) {
			if (path.startsWith("/")) { // 重定向 (startsWith '/' 代表相對路徑)
				response.sendRedirect(request.getContextPath() + path);
			} else { // 請求轉發
				Map<String, Object> model = getModel();
				for (Map.Entry<String, Object> entry : model.entrySet()) {
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppFrontEndPath() + path).forward(request, response);
			}
		}
	}

	@Override
	public String toString() {
		return "View [path=" + path + ", model=" + JsonUtils.toJson(model) + "]";
	}

}
