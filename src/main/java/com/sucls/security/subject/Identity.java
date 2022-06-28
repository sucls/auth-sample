package com.sucls.security.subject;

/**
 *  主体
 * @author sucl
 * @date 2022/6/27 16:53
 * @since 1.0.0
 */
public interface Identity {

    Object getPrincipal();

    boolean isAuthed();

    boolean hasRole(String roleId);

    boolean hasPermission(String permission);

}
