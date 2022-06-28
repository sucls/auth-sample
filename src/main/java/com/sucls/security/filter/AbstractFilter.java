package com.sucls.security.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author sucl
 * @date 2022/6/28 9:03
 * @since 1.0.0
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
