package com.johnson.core.mvc.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory {

	/**
	 * 輸入一個目標類和一組Proxy接口實現, 輸出一個代理對象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			/**
			 * 代理方法, 每次調用目標方法時都會先創建一個 ProxyChain 對象, 然後調用該對象的 doProxyChain() 方法.
			 */
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
						.doProxyChain();
			}
		});
	}
}