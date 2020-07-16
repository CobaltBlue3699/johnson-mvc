package com.johnson.core.mvc.bean;

import com.johnson.core.common.PrimitiveMap;
import com.johnson.core.utils.JsonUtils;

public class Param<K extends Object> extends PrimitiveMap<K> {
	@Override
	public String toString() {
		return "Param [data=" + JsonUtils.toJson(map) + "]";
	}
}
