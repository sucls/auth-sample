package com.sucls.security.resource;

import com.sucls.security.authz.HasRole;
import com.sucls.security.servlet.Path;

import java.util.Properties;

/**
 * @author sucl
 * @date 2022/6/28 15:17
 * @since 1.0.0
 */
@Path("/system")
public class SystemService {

    @HasRole({"ROLE_ADMIN"})
    @Path("/properties")
    public Properties getProperties(){
        return System.getProperties();
    }

    @HasRole({"ROLE_USER"})
    @Path("/env")
    public Object getEnv(){
        return System.getenv();
    }

}
