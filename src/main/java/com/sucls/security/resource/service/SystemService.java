package com.sucls.security.resource.service;

import com.sucls.security.authz.annotation.Access;
import com.sucls.security.resource.Path;

import java.util.Properties;

/**
 * @author sucl
 * @date 2022/6/28 15:17
 * @since 1.0.0
 */
@Path("/system")
public class SystemService {

    @Access({"ROLE_ADMIN"})
    @Path("/properties")
    public Properties getProperties(){
        return System.getProperties();
    }

    @Access({"ROLE_USER"})
    @Path("/env")
    public Object getEnv(){
        return System.getenv();
    }

}
