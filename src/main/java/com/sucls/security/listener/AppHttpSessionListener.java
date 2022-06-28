package com.sucls.security.listener;

import com.sucls.security.util.SessionContextHolder;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author sucl
 * @date 2022/6/27 17:22
 * @since 1.0.0
 */
public class AppHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        if(session!=null){
            SessionContextHolder.removeIdentity(session.getId());
        }
    }
}
