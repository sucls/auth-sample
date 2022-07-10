package com.sucls.security.authc;

/**
 * @author sucl
 * @date 2022/7/10 9:59
 * @since 1.0.0
 */
public interface PasswordEncoder {

    String encode(String pwd);

    boolean matches(String rawPwd,String pwd);

}
