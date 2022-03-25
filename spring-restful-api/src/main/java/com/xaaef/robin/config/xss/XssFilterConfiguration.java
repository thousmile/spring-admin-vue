package com.xaaef.robin.config.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.xaaef.robin.util.JsonUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * <p>
 *
 * </p>
 *
 * @author WangChenChen
 * @version 1.0
 * @date 2022/3/25 9:28
 */


@Configuration
public class XssFilterConfiguration {

    @Bean
    public FilterRegistrationBean<Filter> xssFilterRegister() {
        var registration = new FilterRegistrationBean<>();
        registration.setFilter((servletRequest, servletResponse, filterChain) -> {
            var xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(xssRequest, servletResponse);
        });
        registration.addUrlPatterns("/*");
        registration.setName("XssFilter");
        registration.setOrder(1);
        return registration;
    }

}

