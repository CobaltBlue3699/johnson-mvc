package com.johnson.core.filter.encoding.utils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public final class Connector {

    public ServletRequest createRequest(ServletRequest request) {
        if (request == null) {
            throw new NullPointerException();
        }
        HttpServletRequest h;
        try {
            h = (HttpServletRequest) request;
        } catch (ClassCastException ex) {
            return request;
        }
        return new RequestWrapper(h);
    }

}