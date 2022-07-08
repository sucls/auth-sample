package com.sucls.security.authz;

import com.sucls.security.aop.MethodAccess;
import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author sucl
 * @date 2022/7/8 10:03
 * @since 1.0.0
 */
public class HasPermissionMethodAccess implements MethodAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger(HasPermissionMethodAccess.class);

    @Override
    public boolean supports(Method method) {
        return method.getAnnotation(HasPermission.class)!=null;
    }

    @Override
    public boolean canAccess(Method method) {
        HasPermission hasPermission = method.getAnnotation(HasPermission.class);
        LOGGER.info("权限检查 ：{}", hasPermission.value());
        Identity identity = IdentityHolder.get();
        if(identity != null){
            if(!identity.hasPermission(hasPermission.value())){
                return false;
            }
            return true;
        }
        return false;
    }
}
