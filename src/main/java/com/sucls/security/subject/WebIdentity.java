package com.sucls.security.subject;

import java.util.Set;

/**
 * @author sucl
 * @date 2022/6/27 21:11
 * @since 1.0.0
 */
public class WebIdentity implements Identity{

    private Object principal;

    private boolean authed;

    private Set<String> roleIds;

    private Set<String> permissions;

    public WebIdentity(Object principal){
        this.principal = principal;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthed() {
        return authed;
    }

    @Override
    public boolean hasRole(String roleId) {
        return roleIds!=null && roleIds.contains(roleId);
    }

    @Override
    public boolean hasPermission(String permission) {
        return permissions!=null && permissions.contains(permission);
    }

    public WebIdentity setAuthed(boolean authed) {
        this.authed = authed;
        return this;
    }

    public WebIdentity setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
        return this;
    }

    public WebIdentity setPermissions(Set<String> permissions) {
        this.permissions = permissions;
        return this;
    }
}
