package com.johnson.core.common;

import java.util.Date;
import java.util.List;

import org.w3c.dom.Document;

public interface IMapGetter<K> {

	Object getObject(K key);

	boolean getBool(K key);

	byte getByte(K key);

	char getChar(K key);

	double getDouble(K key);

	float getFloat(K key);

	int getInt(K key);

	long getLong(K key);

	short getShort(K key);

	String getString(K key);

	Document getXml(K key);

	Date getDate(K key);

	IPrimitiveMap<K> getMap(K key);

	List<?> getList(K key);
}
