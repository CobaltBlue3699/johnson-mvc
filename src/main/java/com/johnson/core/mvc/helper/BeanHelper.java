package com.johnson.core.mvc.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.johnson.core.utils.ReflectionUtils;

public final class BeanHelper {

	/**
	 * BEAN_MAP相當於一個bean容器, 擁有項目所有bean的實例
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static {
		// 獲取所有bean
		Set<Class<?>> beanClassSet = ClassHelper.getAllBeanClassSet();
		// 將bean實例化, 並放入bean容器中
		for (Class<?> beanClass : beanClassSet) {
			Object obj = ReflectionUtils.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}

	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 獲取 Bean 實例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
		if (!BEAN_MAP.containsKey(cls)) {
			throw new RuntimeException("can not get bean by class: " + cls);
		}
		return (T) BEAN_MAP.get(cls);
	}

	/**
	 * 設置 Bean 實例
	 */
	public static void setBean(Class<?> cls, Object obj) {
		BEAN_MAP.put(cls, obj);
	}
}
