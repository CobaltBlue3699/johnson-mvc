package com.johnson.core.mvc;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.johnson.core.mvc.bean.Handler;
import com.johnson.core.mvc.bean.Param;
import com.johnson.core.mvc.helper.BeanHelper;
import com.johnson.core.mvc.helper.ConfigHelper;
import com.johnson.core.mvc.helper.ControllerHelper;
import com.johnson.core.mvc.helper.RequestHelper;
import com.johnson.core.mvc.view.IViewModal;
import com.johnson.core.mvc.view.JsonData;
import com.johnson.core.utils.ReflectionUtils;


@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	private static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		// 初始化相關helper
		HelperLoader.init();

		ServletContext servletContext = servletConfig.getServletContext();

		registerServlet(servletContext);

		LOGGER.info("server started.");
	}

	/**
	 * DefaultServlet和JspServlet都是由Web容器創建
	 * org.apache.catalina.servlets.DefaultServlet
	 * org.apache.jasper.servlet.JspServlet
	 */
	private void registerServlet(ServletContext servletContext) {
// 		動態註冊處理JSP的Servlet (拿掉 不想用JSP)
//		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

		// 動態註冊處理靜態資源的默認Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping("/favicon.ico"); // 網站 icon
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		defaultServlet.addMapping(ConfigHelper.getAppFrontEndPath() + "*");
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestMethod = request.getMethod().toUpperCase();
		String requestPath = ObjectUtils.defaultIfNull(request.getPathInfo(), "/");

		// 這裡根據Tomcat的配置路徑有兩種情況, 一種是 "/userList", 另一種是 "/${contextPath}/userList"
		requestPath = requestPath.replaceFirst(request.getContextPath(), "");

		// 根據請求取得處理器(這裡類似於SpringMVC中的映射處理器)
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if (handler != null) {
			
			Object controllerBean = BeanHelper.getBean(handler.getControllerClass());
			// 初始化参数
			Param<String> param = RequestHelper.createParam(request);

			// 調用與請求對應的方法(這裡類似於SpringMVC中的處理器適配器)
			Object result = null;
			Method actionMethod = handler.getControllerMethod();
			if (param == null || param.isEmpty()) {
				result = ReflectionUtils.invokeMethod(controllerBean, actionMethod);
			} else {
				result = ReflectionUtils.invokeMethod(controllerBean, actionMethod, param);
			}

			// 跳轉頁面或返回json數據(這裡類似於SpringMVC中的View解析器)
			if (!(result instanceof IViewModal)) {
				result = new JsonData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "controller must return a ViewModal.");	
			}
			((IViewModal) result).toClient(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "resource not found.");
		}
	}
}
