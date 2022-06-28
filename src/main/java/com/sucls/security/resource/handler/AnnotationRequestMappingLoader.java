package com.sucls.security.resource.handler;

import com.sucls.security.resource.Path;
import com.sucls.security.resource.RequestMapping;
import net.sf.cglib.proxy.Enhancer;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * @author sucl
 * @date 2022/6/28 15:23
 * @since 1.0.0
 */
public class AnnotationRequestMappingLoader implements RequestMappingLoader{

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationRequestMappingLoader.class);

    @Override
    public void load(Map<String, RequestMapping> requestMappings) {
        Reflections reflections = new Reflections();
        Set<Class<?>> serviceClazzs = reflections.getTypesAnnotatedWith(Path.class);

        if(!serviceClazzs.isEmpty()){
            for (Class<?> serviceClazz : serviceClazzs) {
                Path classPath = serviceClazz.getAnnotation(Path.class);
                Set<Method> methods = ReflectionUtils.getMethods(serviceClazz, method -> method.getAnnotation(Path.class) != null);
                if(!methods.isEmpty()){
                    for (Method method : methods) {
                        String requestPath = classPath.value() + method.getAnnotation(Path.class).value();
                        if(requestMappings.containsKey(requestPath)){
                            LOGGER.warn("存在重复服务:{}", requestPath);
                        }

                        LOGGER.info("解析{}服务:{}",serviceClazz, requestPath);

                        Object target = buildTargetProxy(serviceClazz);
                        requestMappings.put(requestPath,new RequestMapping(target, method, method.getParameterTypes()));
                    }
                }
            }
        }
    }

    private Object buildTargetProxy(Class<?> serviceClazz) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(serviceClazz);
        enhancer.setCallback(new TargetMethodInterceptor());
        return enhancer.create();
    }

}
