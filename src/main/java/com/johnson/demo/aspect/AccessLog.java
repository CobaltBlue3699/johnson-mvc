package com.johnson.demo.aspect;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.johnson.core.mvc.annotation.Aspect;
import com.johnson.core.mvc.proxy.AspectProxy;
import com.johnson.core.utils.JsonUtils;

@Aspect(pkg = "com.asuscloud.demo.controller")
public class AccessLog extends AspectProxy {

	private static Logger LOGGER = LogManager.getLogger(AccessLog.class);

	@Override
	public void before(Class<?> targetClass, Method method, Object[] params) throws Throwable {
		LOGGER.info("access " + targetClass.getSimpleName() + " " + method.getName() + "(" + JsonUtils.toJson(params) + ")");
	}

	@Override
	public void after(Class<?> targetClass, Method method, Object[] params, Object result) throws Throwable {
		LOGGER.info("success " + targetClass.getSimpleName() + " " + method.getName() + "(" + JsonUtils.toJson(result) + ")");
	}

	@Override
	public void error(Class<?> targetClass, Method method, Object[] params, Throwable e) {
		LOGGER.error("error " + targetClass.getSimpleName() + " " + method.getName() + ": " + e.getMessage());
	}
}
