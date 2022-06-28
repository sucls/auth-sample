package com.sucls.security.authc;

/**
 * @author sucl
 * @date 2022/6/27 17:25
 * @since 1.0.0
 */
public abstract class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
