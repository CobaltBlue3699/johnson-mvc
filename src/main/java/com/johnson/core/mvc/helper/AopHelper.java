package com.johnson.core.mvc.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.johnson.core.mvc.annotation.Aspect;
import com.johnson.core.mvc.annotation.Service;
import com.johnson.core.mvc.proxy.AspectProxy;
import com.johnson.core.mvc.proxy.Proxy;
import com.johnson.core.mvc.proxy.ProxyFactory;
import com.johnson.core.mvc.proxy.TransactionProxy;
import com.johnson.core.utils.ClassUtils;


/**
 * AOP
 */
public final class AopHelper {

	private static final Logger LOGGER = LogManager.getLogger(AopHelper.class);

	static {
		try {
			// key: 執行的proxy, value: 這些class需要被aop
			Map<Class<?>, Set<Class<?>>> aspectMap = createAspectMap();
			// key: class, value: 這個class的多項proxy
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(aspectMap);
			// 把aop 實踐織入到target class中, 創建proxy
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
				// 覆蓋Bean容器裡target class對應的實例, 下次從Bean容器獲取的就是代理對象了
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			LOGGER.error("aop failure", e);
		}
	}

	/**
	 * 獲取aop-class集合的映射
	 */
	private static Map<Class<?>, Set<Class<?>>> createAspectMap() throws Exception {
		Map<Class<?>, Set<Class<?>>> aspectMap = new HashMap<Class<?>, Set<Class<?>>>();
		addAspectProxy(aspectMap);
		// 自動為所有service加上transaction
		addTransactionProxy(aspectMap);
		return aspectMap;
	}

	/**
	 * 所有實現了AspectProxy的類別 及它們各個需要aop的對象
	 */
	private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
		// 所有實現了AspectProxy的類別
		Set<Class<?>> aspectClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> aspectClass : aspectClassSet) {
			if (aspectClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = aspectClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				aspectMap.put(aspectClass, targetClassSet);
			}
		}
	}

	/**
	 * 取得所有Service並加上Transaction機制 (為可能的Transaction method準備)
	 */
	private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> aspectMap) {
		Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
		aspectMap.put(TransactionProxy.class, serviceClassSet);
	}

	/**
	 * 根據@Aspect定義的package和class去獲取對應的target class集合
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		String pkg = aspect.pkg();
		String cls = aspect.cls();
		// 如果package與class均不為空，則添加指定類
		if (!pkg.equals("") && !cls.equals("")) {
			targetClassSet.add(Class.forName(pkg + "." + cls));
		} else if (!pkg.equals("")) {
			// 如果package不為空, class為空, 則添加該package下所有類
			targetClassSet.addAll(ClassUtils.getClassSet(pkg));
		}
		return targetClassSet;
	}

	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> aspectMap) throws Exception {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : aspectMap.entrySet()) {
			Class<?> aspectClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet) {
				Proxy aspect = (Proxy) aspectClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(aspect);
				} else {
					List<Proxy> aspectList = new ArrayList<Proxy>();
					aspectList.add(aspect);
					targetMap.put(targetClass, aspectList);
				}
			}
		}
		return targetMap;
	}
}
