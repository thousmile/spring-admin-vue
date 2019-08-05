package com.ifsaid.baodao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/16 15:28
 * @describe：
 * @version: 1.0
 */
public class PasswordEncoderTest {

    private final static PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {

        String admin = encoder.encode("root");
        System.out.println(admin);
    }

}
