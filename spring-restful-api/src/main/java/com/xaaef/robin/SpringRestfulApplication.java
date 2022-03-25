package com.xaaef.robin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableAsync
@EnableCaching
@EnableScheduling
@MapperScan("com.xaaef.robin.mapper")
@SpringBootApplication
public class SpringRestfulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestfulApplication.class, args);
    }

}
