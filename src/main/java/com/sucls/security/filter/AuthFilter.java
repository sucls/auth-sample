package com.sucls.security.filter;

import com.sucls.security.authc.AuthToken;
import com.sucls.security.authc.Authenticator;
import com.sucls.security.authc.DefaultAuthenticator;
import com.sucls.security.subject.Identity;
import com.sucls.security.util.AntPathMatcher;
import com.sucls.security.util.IdentityHolder;
import com.sucls.security.util.PatternMatcher;
import com.sucls.security.util.SessionContextHolder;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2022/6/27 17:31
 * @since 1.0.0
 */
public class AuthFilter extends AbstractFilter {

    private String loginUrl = "/login.jsp";

    private String loginSuccessUrl = "/index.html";
    private static PatternMatcher patternMatcher = new AntPathMatcher("/login");

    private static List<PatternMatcher> ignoreMatchers = new ArrayList<>();

    private Authenticator authenticator = new DefaultAuthenticator();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ignorePaths = filterConfig.getInitParameter("ignorePaths");
        if(StringUtils.isNoneBlank(ignorePaths)){
            for (String path : ignorePaths.split(",")) {
                ignoreMatchers.add(new AntPathMatcher(path));
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if(ignoreRequest(httpServletRequest.getRequestURI())){
            chain.doFilter(request,response);
            return;
        }

        if(isAuthed(httpServletRequest) || loginUrl.equals(httpServletRequest.getRequestURI())){ //判断用户是否已经认证
            chain.doFilter(request,response);
            return;
        }else if(isLogin(httpServletRequest)){ // 判断是否登录请求
            Identity identity;
            try {
                identity = loadIdentity(httpServletRequest);
            } catch (Exception e) {
                processAuthException(httpServletRequest,httpServletResponse,e);
                return;
            }

            if(identity != null){
                HttpSession session = httpServletRequest.getSession(true);
                IdentityHolder.set(identity);
                SessionContextHolder.addIdentity(session.getId(),identity);
                httpServletResponse.sendRedirect(loginSuccessUrl);
                return;
            }
        }

        httpServletResponse.sendRedirect(loginUrl);

        return;
    }

    private boolean ignoreRequest(String requestURI) {
        return ignoreMatchers.stream().anyMatch(matcher->matcher.matches(requestURI));
    }

    private void processAuthException(HttpServletRequest request, HttpServletResponse response, Exception e) throws ServletException, IOException {
        request.setAttribute("error",e.getMessage());
        request.getRequestDispatcher(loginUrl).forward(request,response);
    }

    private boolean isLogin(HttpServletRequest httpServletRequest) {
        return patternMatcher.matches(httpServletRequest.getRequestURI()) && StringUtils.endsWithIgnoreCase("post",httpServletRequest.getMethod());
    }

    private boolean isAuthed(HttpServletRequest httpServletRequest) {
        if(IdentityHolder.get()!=null){
            return true;
        }
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null && SessionContextHolder.getIdentity(session.getId())!=null){
            return true;
        }
        return false;
    }

    private Identity loadIdentity(HttpServletRequest request) {
        AuthToken authToken = buildAuthToken(request);

        Identity identity = authenticator.auth(authToken);

        return identity;
    }

    private AuthToken buildAuthToken(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return new AuthToken(username,password);
    }

}
