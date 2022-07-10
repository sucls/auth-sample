package com.sucls.security.authc;

import com.sucls.security.subject.Identity;

/**
 * @author sucl
 * @date 2022/6/27 21:50
 * @since 1.0.0
 */
public interface Authenticator {
    Identity auth(AuthToken authToken);
}
