package com.ifsaid.baodao.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Wang Chen Chen
 * @Description: spring mvc 配置
 * @Date: 18:45 2018/11/18
 */

@Slf4j
@Configuration
@AutoConfigureBefore(WebSecurityConfig.class)
public class SpringMvcConfigurer implements WebMvcConfigurer {

    /**
     * @Description: 允许跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("跨域已设置");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
