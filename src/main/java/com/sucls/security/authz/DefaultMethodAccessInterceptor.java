package com.sucls.security.authz;

import com.sucls.security.aop.MethodAccess;
import com.sucls.security.aop.MethodAccessInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2022/7/8 10:20
 * @since 1.0.0
 */
public class DefaultMethodAccessInterceptor extends MethodAccessInterceptor {

    public DefaultMethodAccessInterceptor() {
        super(initInterceptors());
    }

    private static List<MethodAccess> initInterceptors() {
        List<MethodAccess> methodAccesses = new ArrayList<>();
        methodAccesses.add(new HasRoleMethodAccess());
        methodAccesses.add(new HasPermissionMethodAccess());
        return methodAccesses;
    }

}
