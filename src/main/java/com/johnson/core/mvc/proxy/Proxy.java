package com.johnson.core.mvc.proxy;

/**
 * 	代理接口
 */
public interface Proxy {

    /**
     * 	執行鍊式代理
     * 	所謂鍊式代理, 就是說, 可將多個代理通過一條鍊子串起來, 一個個地去執行, 執行順序取決於添加到鏈上的先後順序
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}