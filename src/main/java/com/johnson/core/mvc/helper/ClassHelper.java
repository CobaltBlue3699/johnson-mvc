package com.johnson.core.mvc.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.johnson.core.mvc.annotation.Bean;
import com.johnson.core.mvc.annotation.Controller;
import com.johnson.core.mvc.annotation.Service;
import com.johnson.core.utils.ClassUtils;

public final class ClassHelper {

	/**
	 * 定義類集合（存放package下的所有類）
	 */
	private static final Set<Class<?>> CLASS_SET;

	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		// 獲取package下所有類
		CLASS_SET = ClassUtils.getClassSet(basePackage);
	}

	/**
	 * 獲取package下所有類
	 */
	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}
	
	/**
	 * 獲取package下所有bean
	 */
	public static Set<Class<?>> getAllBeanClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getControllerClassSet());
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getBeanClassSet());
		return beanClassSet;
	}
	
	/**
	 * 獲取package下所有bean
	 */
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getClassSetByAnnotation(Bean.class));
		return beanClassSet;
	}
	
	/**
	 * 獲取package下所有Controller
	 */
	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getClassSetByAnnotation(Controller.class));
		return beanClassSet;
	}
	
	/**
	 * 獲取package下所有Service
	 */
	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getClassSetByAnnotation(Service.class));
		return beanClassSet;
	}

	/**
	 * 獲取package下某父類的所有子類 或某接口的所有實現
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			// isAssignableFrom() 指 superClass 和 cls 是否相同或 superClass 是否是 cls 的父類/接口
			if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}

	/**
	 * 獲取package下帶有某註解的所有Class
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> cls : CLASS_SET) {
			if (cls.isAnnotationPresent(annotationClass)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
}
