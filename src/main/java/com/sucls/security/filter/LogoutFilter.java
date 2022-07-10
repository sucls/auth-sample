package com.sucls.security.filter;

import com.sucls.security.util.AntPathMatcher;
import com.sucls.security.util.IdentityHolder;
import com.sucls.security.util.PatternMatcher;
import com.sucls.security.util.SessionContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author sucl
 * @date 2022/6/27 17:35
 * @since 1.0.0
 */
public class LogoutFilter extends AbstractFilter {

    private static PatternMatcher patternMatcher = new AntPathMatcher("/logout");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        if(!patternMatcher.matches(httpServletRequest.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }else{
            //删除session identity
            HttpSession session = httpServletRequest.getSession(false);
            if(session != null){
                SessionContextHolder.removeIdentity(session.getId());
                // 让session失效
                session.invalidate();
                // 删除上下文Identity
                IdentityHolder.remove();
            }
            chain.doFilter(request,response);
        }
    }

}
