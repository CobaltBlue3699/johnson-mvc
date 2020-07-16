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

import com.johnson.core.filter.cache.utils.CacheConfigParameter;
import com.johnson.core.filter.cache.utils.Cacheability;
import com.johnson.core.filter.cache.utils.HTTPCacheHeader;

public class CacheFilter implements Filter {

	private long expiration;

	private Cacheability cacheability;

	private boolean mustRevalidate;

	private String vary;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		try {
			expiration = Long.valueOf(filterConfig.getInitParameter(CacheConfigParameter.EXPIRATION.getName()));
		} catch (NumberFormatException e) {
			throw new ServletException(
					new StringBuilder("The initialization parameter ").append(CacheConfigParameter.EXPIRATION.getName())
							.append(" is invalid or is missing for the filter ").append(filterConfig.getFilterName())
							.append(".").toString());
		}
		cacheability = Boolean.valueOf(filterConfig.getInitParameter(CacheConfigParameter.PRIVATE.getName()))
				? Cacheability.PRIVATE
				: Cacheability.PUBLIC;
		mustRevalidate = Boolean.valueOf(filterConfig.getInitParameter(CacheConfigParameter.MUST_REVALIDATE.getName()));
		vary = filterConfig.getInitParameter(CacheConfigParameter.VARY.getName());
	}

	/**
	 * <p>
	 * Set HTTP cache headers.
	 * </p>
	 * {@inheritDoc}
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		StringBuilder cacheControl = new StringBuilder(cacheability.getValue()).append(", max-age=").append(expiration);
		if (mustRevalidate) {
			cacheControl.append(", must-revalidate");
		}

		// Set cache directives
		httpServletResponse.setHeader(HTTPCacheHeader.CACHE_CONTROL.getName(), cacheControl.toString());
		httpServletResponse.setDateHeader(HTTPCacheHeader.EXPIRES.getName(),
				System.currentTimeMillis() + expiration * 1000L);

		// Set Vary field
		if (vary != null && !vary.isEmpty()) {
			httpServletResponse.setHeader(HTTPCacheHeader.VARY.getName(), vary);
		}

		/*
		 * By default, some servers (e.g. Tomcat) will set headers on any SSL content to
		 * deny caching. Omitting the Pragma header takes care of user-agents
		 * implementing HTTP/1.0.
		 */
		filterChain.doFilter(servletRequest, new HttpServletResponseWrapper(httpServletResponse) {
			@Override
			public void addHeader(String name, String value) {
				if (!HTTPCacheHeader.PRAGMA.getName().equalsIgnoreCase(name)) {
					super.addHeader(name, value);
				}
			}

			@Override
			public void setHeader(String name, String value) {
				if (!HTTPCacheHeader.PRAGMA.getName().equalsIgnoreCase(name)) {
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