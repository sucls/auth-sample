package com.sucls.security.filter;

import com.sucls.security.authc.AuthenticationException;
import com.sucls.security.authz.AccessDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author sucl
 * @date 2022/6/27 17:36
 * @since 1.0.0
 */
public class ExceptionHandlerFilter extends AbstractFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request,response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            if(e instanceof AuthenticationException){
                // todo
                LOGGER.info("AuthenticationException:{}",e.getMessage());
            }
            if(e instanceof AccessDeniedException){
                // todo
                LOGGER.info("AccessDeniedException:{}",e.getMessage());
            }
        }
    }

}
