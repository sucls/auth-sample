package com.sucls.security.aop;

import java.lang.reflect.Method;

/**
 * @author sucl
 * @date 2022/7/8 10:01
 * @since 1.0.0
 */
public interface MethodAccess {

    boolean supports(Method method);

    boolean canAccess(Method method);
}
