package com.sucls.security.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author sucl
 * @date 2022/7/8 9:29
 * @since 1.0.0
 */
public class AopUtils {

    private static MethodInterceptor interceptor = new EmptyMethodInterceptor();

    /**
     *
     * @param type
     * @return
     * @param <T>
     */
    public static <T> T createProxy(Class<T> type) {
        return createProxy(type, interceptor);
    }

    /**
     *
     * @param type
     * @param methodInterceptor
     * @return
     * @param <T>
     */
    public static <T> T createProxy(Class<T> type, MethodInterceptor methodInterceptor) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(methodInterceptor);
        return (T) enhancer.create();
    }

    public static class EmptyMethodInterceptor implements MethodInterceptor{

        @Override
        public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(target,args);
        }
    }

}
