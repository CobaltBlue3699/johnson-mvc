package com.johnson.core.mvc.proxy;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> targetClass = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();

		begin();
		try {
			if (isIntercept(method, params)) {
				before(targetClass, method, params);
				result = proxyChain.doProxyChain();
				after(targetClass, method, params, result);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			error(targetClass, method, params, e);
			throw e;
		} finally {
			end();
		}

		return result;
	}

	public void begin() {
	}

	public boolean isIntercept(Method method, Object[] params) throws Throwable {
		return true;
	}

	public void before(Class<?> targetClass, Method method, Object[] params) throws Throwable {
	}

	public void after(Class<?> targetClass, Method method, Object[] params, Object result) throws Throwable {
	}

	public void error(Class<?> targetClass, Method method, Object[] params, Throwable e) {
	}

	public void end() {
	}
}