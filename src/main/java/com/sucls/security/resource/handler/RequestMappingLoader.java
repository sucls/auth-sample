package com.sucls.security.resource.handler;

import com.sucls.security.resource.Path;
import com.sucls.security.resource.RequestMapping;

import java.util.Map;

/**
 * @author sucl
 * @date 2022/6/28 15:23
 * @since 1.0.0
 */
public interface RequestMappingLoader {
    void load(Map<String, RequestMapping> requestMappings);
}
