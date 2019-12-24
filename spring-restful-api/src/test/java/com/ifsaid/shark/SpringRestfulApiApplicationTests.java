package com.ifsaid.shark;

import org.junit.jupiter.api.Test;

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

}
