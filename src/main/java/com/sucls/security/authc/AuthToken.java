package com.sucls.security.authc;

import java.util.Set;

/**
 * @author sucl
 * @date 2022/6/27 21:35
 * @since 1.0.0
 */
public class AuthToken {

    private Object principal;

    private Object credentials;

    private Set<String> roleIds;

    private Set<String> permissions;

    public AuthToken(Object principal, Object credentials){
        this.principal = principal;
        this.credentials = credentials;
    }

    public Object getPrincipal() {
        return principal;
    }

    public AuthToken setPrincipal(Object principal) {
        this.principal = principal;
        return this;
    }

    public Object getCredentials() {
        return credentials;
    }

    public AuthToken setCredentials(Object credentials) {
        this.credentials = credentials;
        return this;
    }

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public AuthToken setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public AuthToken setPermissions(Set<String> permissions) {
        this.permissions = permissions;
        return this;
    }
}
