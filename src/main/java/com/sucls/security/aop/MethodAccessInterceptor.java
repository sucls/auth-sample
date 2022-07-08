package com.sucls.security.aop;

import com.sucls.security.authz.HasRole;
import com.sucls.security.servlet.ResponseResult;
import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author sucl
 * @date 2022/6/28 15:57
 * @since 1.0.0
 */
public abstract class MethodAccessInterceptor implements MethodInterceptor {

    private List<MethodAccess> methodAccesses;

    public MethodAccessInterceptor(List<MethodAccess> methodAccesses) {
        this.methodAccesses = methodAccesses;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy proxy) throws Throwable {

        if(!checkIdentityAccessPermission(method)){
            return ResponseResult.error("1111","没有访问权限");
        }

        return proxy.invokeSuper(obj, params);
    }

    private boolean checkIdentityAccessPermission(Method method) {
        if(methodAccesses != null){
            for (MethodAccess methodAccess : methodAccesses) {
                if(methodAccess.supports(method)){
                    if(!methodAccess.canAccess(method)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
