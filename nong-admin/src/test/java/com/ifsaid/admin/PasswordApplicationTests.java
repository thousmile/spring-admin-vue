package com.ifsaid.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 14:55
 * @describe：
 * @version: 1.0
 */
public class PasswordApplicationTests {



    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String admin = passwordEncoder.encode("admin");
        String test = passwordEncoder.encode("test");
        System.out.println(admin);
        System.out.println(test);
    }
    
}
