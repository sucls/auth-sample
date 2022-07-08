package com.sucls.security.servlet;

import com.sucls.security.aop.MethodAccessInterceptor;
import com.sucls.security.aop.AopUtils;
import com.sucls.security.authz.DefaultMethodAccessInterceptor;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author sucl
 * @date 2022/6/28 15:23
 * @since 1.0.0
 */
public class RequestPathParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestPathParser.class);

    public Map<String, RequestInfo> parsePath() {
        Map<String, RequestInfo> requestInfoMap = new HashMap<>();

        Reflections reflections = new Reflections();
        Set<Class<?>> serviceClazzs = reflections.getTypesAnnotatedWith(Path.class);

        if(!serviceClazzs.isEmpty()){
            for (Class<?> serviceClazz : serviceClazzs) {
                Path classPath = serviceClazz.getAnnotation(Path.class);
                Set<Method> methods = ReflectionUtils.getMethods(serviceClazz, method -> method.getAnnotation(Path.class) != null);
                if(!methods.isEmpty()){
                    for (Method method : methods) {
                        String requestPath = classPath.value() + method.getAnnotation(Path.class).value();
                        if(requestInfoMap.containsKey(requestPath)){
                            LOGGER.warn("存在重复服务:{}", requestPath);
                        }

                        LOGGER.info("解析{}服务:{}",serviceClazz, requestPath);

                        Object target = AopUtils.createProxy(serviceClazz, new DefaultMethodAccessInterceptor());
                        requestInfoMap.put(requestPath,new RequestInfo(target, method, method.getParameterTypes()));
                    }
                }
            }
        }

        return requestInfoMap;
    }


}
