package com.johnson.core.filter.encoding.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

final class Parameters {

    private final Map<String, ArrayList<String>> paramHashValues
            = new LinkedHashMap<String, ArrayList<String>>();

    public String[] getParameterValues(String name) {
        ArrayList<String> values = paramHashValues.get(name);
        if (values == null) {
            return null;
        }
        return values.toArray(new String[values.size()]);
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(paramHashValues.keySet());
    }

    public String getParameter(String name) {
        ArrayList<String> values = paramHashValues.get(name);
        if (values != null) {
            /*if (values.isEmpty()) { // will never happen
             return "";
             }*/
            return values.get(0);
        } else {
            return null;
        }
    }

    int size() {
        return paramHashValues.size();
    }

    Parameters addParameter(String key, String value) {

        if (key == null) {
            return this;
        }

        ArrayList<String> values = paramHashValues.get(key);
        if (values == null) {
            values = new ArrayList<String>(1);
            values.add(value);
            paramHashValues.put(key, values);
        } else {
            values.add(value);
        }
        return this;
    }

}