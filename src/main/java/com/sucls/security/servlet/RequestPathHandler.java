package com.sucls.security.servlet;

import com.google.gson.Gson;
import com.sucls.security.subject.Identity;
import com.sucls.security.util.IdentityHolder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2022/6/28 15:12
 * @since 1.0.0
 */
public class RequestPathHandler{

    public void handle(RequestInfo requestInfo, HttpServletRequest request, HttpServletResponse response) {
        Object service = requestInfo.getTarget();
        Method method = requestInfo.getMethod();
        Object[] params = buildServiceMethodParameters(requestInfo,request,response);
        try {
            Object result = method.invoke(service, params);
            handleResponse(result,request,response);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleResponse(Object result, HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson().newBuilder().create();
        String jsonResult = gson.toJson(toResponseResult(result));

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer;
        try {
            writer = response.getWriter();
            writer.println(jsonResult);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseResult toResponseResult(Object result) {
        if(result != null && result instanceof ResponseResult){
            return (ResponseResult) result;
        }
        return ResponseResult.ok(result);
    }

    private Object[] buildServiceMethodParameters(RequestInfo requestInfo, HttpServletRequest request, HttpServletResponse response) {
        Class[] params = requestInfo.getParams();
        if(params == null || params.length ==0){
            return new Object[]{};
        }else{
            List<Object> parameters = new ArrayList<>();
            for (Class paramClazz : params) {
                parameters.add(resolveServiceParam(paramClazz,request,response));
            }
            return parameters.toArray(new Object[0]);
        }
    }

    private Object resolveServiceParam(Class<?> paramClazz, HttpServletRequest request, HttpServletResponse response) {
        if(ServletRequest.class.isAssignableFrom(paramClazz)){
            return request;
        }else if(ServletResponse.class.isAssignableFrom(paramClazz)){
            return response;
        }else if(Identity.class.isAssignableFrom(paramClazz)){
            return IdentityHolder.get();
        }
        // todo ...
        return null;
    }
}
