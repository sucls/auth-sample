package com.sucls.security.authc;

/**
 * 账号不存在
 * @author sucl
 * @date 2022/6/28 9:58
 * @since 1.0.0
 */
public class AccountNotExistsException extends AuthenticationException{

    public AccountNotExistsException(String message) {
        super(message);
    }

}
