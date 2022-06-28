package com.sucls.security.authz;

/**
 * @author sucl
 * @date 2022/6/27 17:26
 * @since 1.0.0
 */
public abstract class AccessDeniedException extends RuntimeException{

    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
