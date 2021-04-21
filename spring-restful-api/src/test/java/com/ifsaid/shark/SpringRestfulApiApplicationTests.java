package com.ifsaid.shark;

import com.ifsaid.shark.common.jwt.JwtTokenUtils;
import com.ifsaid.shark.common.jwt.JwtUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SpringRestfulApiApplicationTests {

    @Test
    void contextLoads() {
        String originalFileName = "dwad.jpg";
        String reg = ".+(.JPEG|.jpeg|.JPG|.jpg|.png|.PNG)$";
        Matcher matcher = Pattern.compile(reg).matcher(originalFileName);
        System.out.println(matcher.find());

    }


    @Test
    void testJwt() {
        JwtTokenUtils jwt = new JwtTokenUtils();
        jwt.setTokenHeader("Authorization");
        jwt.setTokenHead("Bearer");
        jwt.setExpiration(10L);
        JwtUser user = new JwtUser();
        user.setUsername("admin");
        String token = jwt.generateToken(user);
        System.out.println("token: " + token);
        String username = jwt.getUsernameFromToken(token);
        System.out.println("username: " + username);

    }

    @Test
    void testJwt2() {
        JwtTokenUtils jwt = new JwtTokenUtils();
        jwt.setTokenHeader("Authorization");
        jwt.setTokenHead("Bearer");
        jwt.setExpiration(10L);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYxODk4OTM0M30.PapWQ1-_J5NIcN8JuhDigUngHnqAtErEz3mbdP44bg8";
        String username = jwt.getUsernameFromToken(token);
        System.out.println("username: " + username);
    }

}
