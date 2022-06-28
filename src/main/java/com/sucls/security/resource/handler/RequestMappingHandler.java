package com.sucls.security.resource.handler;

import com.sucls.security.resource.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sucl
 * @date 2022/6/28 15:11
 * @since 1.0.0
 */
public interface RequestMappingHandler {

    void handle(RequestMapping requestMapping, HttpServletRequest request, HttpServletResponse response);
}
