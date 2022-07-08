package com.sucls.security.authc;

/**
 * 密码错误
 * @author sucl
 * @date 2022/6/28 9:58
 * @since 1.0.0
 */
public class CredentialsInvalidException extends AuthenticationException{

    public CredentialsInvalidException(String message) {
        super(message);
    }

}
