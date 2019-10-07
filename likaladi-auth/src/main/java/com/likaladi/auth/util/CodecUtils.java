package com.likaladi.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: likaladi
 * @Feature: 密码加密
 */
public class CodecUtils {

    public static String passwordBcryptEncode(String username,String password){

        return new BCryptPasswordEncoder().encode(username + password);
    }

    public static Boolean passwordConfirm(String rawPassword,String encodePassword){
        return new BCryptPasswordEncoder().matches(rawPassword,encodePassword);
    }
}
