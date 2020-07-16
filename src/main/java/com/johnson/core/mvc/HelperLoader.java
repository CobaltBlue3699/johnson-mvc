package com.johnson.core.mvc;

import com.johnson.core.mvc.helper.AopHelper;
import com.johnson.core.mvc.helper.BeanHelper;
import com.johnson.core.mvc.helper.ClassHelper;
import com.johnson.core.mvc.helper.ControllerHelper;
import com.johnson.core.mvc.helper.IocHelper;
import com.johnson.core.utils.ClassUtils;

public final class HelperLoader {

	/**
	 * 執行static初始化區塊
	 */
	public static void init() {
		// 要乖乖照順序阿你們
		Class<?>[] classList = {
			ClassHelper.class,
			BeanHelper.class,
			AopHelper.class,
			IocHelper.class,
			ControllerHelper.class
		};
		for (Class<?> cls : classList) {
			ClassUtils.loadClass(cls.getName());
		}
	}

}