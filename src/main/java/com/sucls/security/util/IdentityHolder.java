package com.sucls.security.util;

import com.sucls.security.subject.Identity;

/**
 * @author sucl
 * @date 2022/6/27 20:59
 * @since 1.0.0
 */
public class IdentityHolder {

    private static ThreadLocal<Identity> threadLocal = new ThreadLocal<>();

    public static void set(Identity identity){
        threadLocal.set(identity);
    }

    public static Identity get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
