package com.johnson.core.mvc.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.johnson.core.mvc.annotation.Autowired;
import com.johnson.core.utils.ReflectionUtils;

public final class IocHelper {

    /**
     * 	遍歷bean容器所有bean的屬性, 為所有帶@Autowired註解的屬性注入實例
     */
    static {
        // 遍歷bean容器裡的所有bean
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //暴力反射獲取屬性
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Autowired.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            beanFieldClass = findImplementClass(beanFieldClass);
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtils.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 	獲取接口對應的實現類
     */
    public static Class<?> findImplementClass(Class<?> interfaceClass) {
        Class<?> implementClass = interfaceClass;
        //接口對應的所有實現類
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(interfaceClass);
        if (CollectionUtils.isNotEmpty(classSetBySuper)) {
            //獲取第一個實現類=> 也許可改成抓取bean.property 或者打config center ?
            implementClass = classSetBySuper.iterator().next();
        }
        return implementClass;
    }
}
