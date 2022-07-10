package com.sucls.security.authc;

import com.sucls.security.subject.Identity;
import com.sucls.security.subject.WebIdentity;

import java.util.*;

/**
 * @author sucl
 * @date 2022/6/27 21:50
 * @since 1.0.0
 */
public class DefaultAuthenticator implements Authenticator {

    private static Map<String, List<String>> userMap = new HashMap<>();

    private PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

    private String defaultPwd = passwordEncoder.encode("123456");

    static {
        userMap.put("admin", Arrays.asList("ROLE_ADMIN"));
        userMap.put("user", Arrays.asList("ROLE_USER"));
    }

    @Override
    public Identity auth(AuthToken authToken) {
        Object principal = authToken.getPrincipal();

        if(!userMap.containsKey(principal)){
            throw new AccountNotExistsException(String.format("账号【%s】不存在",principal));
        }

        if(!checkCredentials(authToken)){
            throw new CredentialsInvalidException(String.format("账号【%s】凭证无效",principal));
        }

        WebIdentity identity = new WebIdentity(principal);
        identity.setRoleIds(new HashSet<>(userMap.get(principal)));
        identity.setAuthed(true); //
        return identity;
    }

    private boolean checkCredentials(AuthToken authToken) {
        return passwordEncoder.matches(authToken.getCredentials().toString(),defaultPwd);
    }
}
