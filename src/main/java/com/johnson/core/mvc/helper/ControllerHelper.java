package com.johnson.core.mvc.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.johnson.core.mvc.annotation.RequestMapping;
import com.johnson.core.mvc.bean.Handler;
import com.johnson.core.mvc.bean.Request;

public final class ControllerHelper {
	
	private static final Logger LOGGER = LogManager.getLogger(ControllerHelper.class);
	
	/**
	 * REQUEST_MAP為 "請求-處理器" 的映射
	 */
	private static final Map<Request, Handler> REQUEST_MAP = new HashMap<Request, Handler>();

	static {
		// 遍歷所有Controller
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (CollectionUtils.isNotEmpty(controllerClassSet)) {
			for (Class<?> controllerClass : controllerClassSet) {
				String baseUrl = "";
				if(controllerClass.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
					baseUrl = controllerRequestMapping.value();
				}
				// 暴力反射獲取所有方法
				Method[] methods = controllerClass.getDeclaredMethods();
				// 遍歷方法
				if (ArrayUtils.isNotEmpty(methods)) {
					for (Method method : methods) {
						// 判斷是否帶RequestMapping註解
						if (method.isAnnotationPresent(RequestMapping.class)) {
							RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
							// 請求路徑
							String requestPath = baseUrl + requestMapping.value();
							// 請求方法
							String requestMethod = requestMapping.method().name();

							// 封裝請求和處理器
							Request request = new Request(requestMethod, requestPath);
							Handler handler = new Handler(controllerClass, method);
							LOGGER.trace(requestMethod + " " + requestPath + ": " + controllerClass.getSimpleName() + "(" + method.getName() + ")");
							REQUEST_MAP.put(request, handler);
						}
					}
				}
			}
		}
	}

	/**
	 * 獲取 Handler
	 */
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return REQUEST_MAP.get(request);
	}
}
