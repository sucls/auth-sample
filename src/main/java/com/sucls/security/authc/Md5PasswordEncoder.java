package com.sucls.security.authc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author sucl
 * @date 2022/7/10 10:01
 * @since 1.0.0
 */
public class Md5PasswordEncoder implements PasswordEncoder{

    private MessageDigest md5;

    public Md5PasswordEncoder(){
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encode(String pwd) {
        return new String( md5.digest(pwd.getBytes()) );
    }

    @Override
    public boolean matches(String rawPwd, String pwd) {
        return Objects.equals(encode(rawPwd), pwd);
    }
}
