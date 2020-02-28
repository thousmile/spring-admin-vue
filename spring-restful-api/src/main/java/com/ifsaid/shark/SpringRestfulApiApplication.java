package com.ifsaid.shark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * Spring Boot 启动类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 https://www.xaaef.com Inc. All rights reserved.
 */

@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.ifsaid.shark.mapper")
@SpringBootApplication
public class SpringRestfulApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestfulApiApplication.class, args);
    }

}
