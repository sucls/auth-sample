package com.sucls.security.filter;

import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;
import com.sucls.security.util.SessionContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sucl
 * @date 2022/6/27 17:32
 * @since 1.0.0
 */
public class SessionContextFilter extends AbstractFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionContextFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        HttpSession session = httpServletRequest.getSession(false);
        if(session != null){
            Identity identity = SessionContextHolder.getIdentity(session.getId());
            if(identity != null){
                IdentityHolder.set(identity);
            }
        }
        
        chain.doFilter(request,response);

        IdentityHolder.remove();
    }

}
