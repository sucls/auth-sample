package com.sucls.security.filter;

import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;
import com.sucls.security.util.SessionContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *  从Session获取Identity并保存到上下文
 * @author sucl
 * @date 2022/6/27 17:32
 * @since 1.0.0
 */
public class SessionContextFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 从Session获取Identity
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null){
            // 将Identity保存到上下文
            Identity identity = SessionContextHolder.getIdentity(session.getId());
            if(identity != null){
                IdentityHolder.set(identity);
            }
        }
        
        chain.doFilter(request,response);
        // 清理
        IdentityHolder.remove();
    }

}
