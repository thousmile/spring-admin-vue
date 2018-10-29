package com.ifsaid.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.ifsaid.admin.mapper")
@SpringBootApplication
public class NongAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(NongAdminApplication.class, args);
    }
}
