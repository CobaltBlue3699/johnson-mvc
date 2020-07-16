package com.johnson.demo.aspect;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.johnson.core.mvc.proxy.AspectProxy;

//@Aspect(pkg = "com.asuscloud.demo.controller")
public class EfficientAspect extends AspectProxy {

	private long begin;

	@Override
	public boolean isIntercept(Method method, Object[] params) throws Throwable {
		return true;// method.getName().equals("userDownload");
	}

	@Override
	public void before(Class<?> targetClass, Method method, Object[] params) throws Throwable {
		Logger targetLogger = LogManager.getLogger(targetClass);
		targetLogger.debug("---------- begin " + method.getName() + " ----------");
		begin = System.currentTimeMillis();
	}

	@Override
	public void after(Class<?> targetClass, Method method, Object[] params, Object result) throws Throwable {
		Logger targetLogger = LogManager.getLogger(targetClass);
		targetLogger.debug(String.format("time: %dms", System.currentTimeMillis() - begin));
		targetLogger.debug("----------- end " + method.getName() + " -----------");
	}
}
