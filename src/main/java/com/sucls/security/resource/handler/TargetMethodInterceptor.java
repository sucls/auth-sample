package com.sucls.security.resource.handler;

import com.sucls.security.authz.annotation.Access;
import com.sucls.security.resource.ResponseResult;
import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author sucl
 * @date 2022/6/28 15:57
 * @since 1.0.0
 */
public class TargetMethodInterceptor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TargetMethodInterceptor.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {

        Access access = method.getAnnotation(Access.class);
        if(access !=null && !checkMethodAccess(access)){
            return ResponseResult.error("1111","没有访问权限");
        }
        Object result = proxy.invokeSuper(obj, params);

        return result;
    }

    private boolean checkMethodAccess(Access access) {
        LOGGER.info("权限检查 ：{}", Arrays.toString(access.value()));
        Identity identity = IdentityHolder.get();
        if(identity != null){
            for (String role : access.value()) {
                if(!identity.hasRole(role)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
