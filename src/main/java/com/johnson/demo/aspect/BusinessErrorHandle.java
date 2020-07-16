package com.johnson.demo.aspect;

import com.johnson.core.error.BusinessException;
import com.johnson.core.mvc.annotation.Aspect;
import com.johnson.core.mvc.proxy.AspectProxy;
import com.johnson.core.mvc.proxy.ProxyChain;
import com.johnson.core.mvc.view.JsonData;

@Aspect(pkg = "com.asuscloud.demo.controller")
public class BusinessErrorHandle extends AspectProxy {
	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		try {
			return super.doProxy(proxyChain);
		} catch (BusinessException e) {
			JsonData data = new JsonData(e.getStatus(), e.getMessage());
			return data;
		}
	}
}
