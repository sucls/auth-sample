package com.sucls.security.session;

import javax.servlet.http.HttpSession;

/**
 * @author sucl
 * @date 2022/6/27 17:16
 * @since 1.0.0
 */
public interface SessionManager {

    HttpSession getSession(String sessionKey);

}
