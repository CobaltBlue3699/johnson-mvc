package com.johnson.core.filter.cache;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.johnson.core.filter.cache.utils.HTTPCacheHeader;

public class NoETagFilter implements Filter {

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * <p>
     * Disables {@code ETag} HTTP header.
     * </p>
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(servletRequest, new HttpServletResponseWrapper((HttpServletResponse) servletResponse) {
            @Override
            public void setHeader(String name, String value) {
                if (!HTTPCacheHeader.ETAG.getName().equalsIgnoreCase(name)) {
                    super.setHeader(name, value);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
    }
}