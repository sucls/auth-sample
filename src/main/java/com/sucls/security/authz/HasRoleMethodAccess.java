package com.sucls.security.authz;

import com.sucls.security.aop.MethodAccess;
import com.sucls.security.aop.MethodAccessInterceptor;
import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author sucl
 * @date 2022/7/8 10:03
 * @since 1.0.0
 */
public class HasRoleMethodAccess implements MethodAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger(HasRoleMethodAccess.class);

    @Override
    public boolean supports(Method method) {
        return method.getAnnotation(HasRole.class)!=null;
    }

    @Override
    public boolean canAccess(Method method) {
        HasRole hasRole = method.getAnnotation(HasRole.class);
        LOGGER.info("角色检查 ：{}", Arrays.toString(hasRole.value()));
        Identity identity = IdentityHolder.get();
        if(identity != null){
            for (String role : hasRole.value()) {
                if(!identity.hasRole(role)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
