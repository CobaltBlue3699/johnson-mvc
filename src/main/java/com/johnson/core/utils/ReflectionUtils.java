package com.johnson.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ReflectionUtils {

//	private static final Logger LOGGER = LogManager.getLogger(ReflectionUtils.class);

	public static Object newInstance(Class<?> cls) {
		Object instance;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
//			LOGGER.error("new instance failure", e);
			throw new RuntimeException(e);
		}
		return instance;
	}

	public static Object newInstance(String className) {
		Class<?> cls = ClassUtils.loadClass(className);
		return newInstance(cls);
	}

	public static Object invokeMethod(Object obj, Method method, Object... args) {
		Object result;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, args);
		} catch (Exception e) {
//			LOGGER.error("invoke method failure", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public static void setField(Object obj, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
//			LOGGER.error("set field failure", e);
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> valueObjectToMap(Object vo)
			throws SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		Class<?> clazz = vo.getClass();
		Method[] methods = clazz.getMethods();
		Map<String, Object> voMap = new HashMap<String, Object>();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().startsWith("get") && !methods[i].getName().equals("getClass")) {
				String properity = methods[i].getName().substring(3).toLowerCase();
				Object value = methods[i].invoke(vo);
				voMap.put(properity, value);
			}
		}
		return voMap;
	}

	public static Object invokeMethod(Object target, String methodName, List<?> args) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		Class<?> clazz = target.getClass();
		Class<?>[] methodArgs = null;
		Object[] methodArgObjs = null;
		Method method = null;
		if (args != null) {
			methodArgs = new Class[args.size()];
			methodArgObjs = new Object[args.size()];
			for (int i = 0; i < args.size(); i++) {
				methodArgs[i] = args.get(i).getClass();
				methodArgObjs[i] = args.get(i);
			}
		}

		method = clazz.getMethod(methodName, methodArgs);
		return method.invoke(target, methodArgObjs);
	}
}
