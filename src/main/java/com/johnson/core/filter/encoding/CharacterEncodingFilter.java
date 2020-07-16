package com.johnson.core.filter.encoding;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.johnson.core.filter.encoding.utils.Connector;

public class CharacterEncodingFilter implements Filter {

    private String characterEncoding = "UTF-8";
    private boolean setResponseCharacterEncoding = false;
    private final Connector connector = new Connector();

    public CharacterEncodingFilter() {
    }

    public CharacterEncodingFilter(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest createRequest = connector.createRequest(request);
        createRequest.setCharacterEncoding(characterEncoding);
        if (setResponseCharacterEncoding) {
            response.setCharacterEncoding(characterEncoding);
        }
        chain.doFilter(createRequest, response);
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public CharacterEncodingFilter setResponseCharacterEncoding(boolean setResponseCharacterEncoding) {
        this.setResponseCharacterEncoding = setResponseCharacterEncoding;
        return this;
    }

    public CharacterEncodingFilter setResponseCharacterEncoding() {
        return setResponseCharacterEncoding(true);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Enumeration<?> e = filterConfig.getInitParameterNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement().toString();
            String upperCase = name.toUpperCase(Locale.US);
            if (upperCase.contains("ENCODING")) {
                String value = filterConfig.getInitParameter(name);
                if (value != null) {
                    if (upperCase.contains("RESPONSE")) {
                        setResponseCharacterEncoding = Boolean.parseBoolean(value);
                    } else {
                        setCharacterEncoding(value);
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {
    }
}