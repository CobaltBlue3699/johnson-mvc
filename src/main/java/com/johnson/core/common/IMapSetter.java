package com.johnson.core.common;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

public interface IMapSetter<K> {

	void setObject(K key, Object value);

	void setBool(K key, boolean value);

	void setByte(K key, byte value);

	void setChar(K key, char value);

	void setDouble(K key, double value);

	void setFloat(K key, float value);

	void setInt(K key, int value);

	void setLong(K key, long value);

	void setShort(K key, short value);

	void setString(K key, String value);

	void setXml(K key, Document value);

	void setDate(K key, Date value);

	void setMap(K key, Map<?, ?> value);

	void setList(K key, List<?> value);
}
