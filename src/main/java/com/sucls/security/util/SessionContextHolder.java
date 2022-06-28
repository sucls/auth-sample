package com.sucls.security.util;

import com.sucls.security.subject.Identity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sucl
 * @date 2022/6/27 17:40
 * @since 1.0.0
 */
public class SessionContextHolder {

    private static SessionContextHolder sessionContextHolder = new SessionContextHolder();

    private static final Map<String,Identity> identityHolder = new ConcurrentHashMap<>();

    private SessionContextHolder(){}

    public static SessionContextHolder getSessionContextHolder(){
        return sessionContextHolder;
    }

    public static Identity getIdentity(String key){
        return identityHolder.get(key);
    }

    public static void addIdentity(String key,Identity identity){
        identityHolder.put(key,identity);
    }

    public static void removeIdentity(String key){
        identityHolder.remove(key);
    }

}
