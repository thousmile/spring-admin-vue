package com.xaaef.robin.config;

import com.xaaef.robin.jwt.JwtAuthenticationTokenFilter;
import com.xaaef.robin.jwt.JwtSecurityUtils;
import com.xaaef.robin.jwt.JwtTokenProperties;
import com.xaaef.robin.jwt.JwtAccessDeniedHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * spring Security 核心配置类
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProperties props;

    private final UserDetailsService userDetailsService;

    private final JwtAuthenticationTokenFilter tokenFilter;

    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(this.userDetailsService)
                .passwordEncoder(JwtSecurityUtils.getPasswordEncoder());
    }


    /**
     * spring Security 配置
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                //未授权处理
                .exceptionHandling()
                // 自定义未登录返回
                .authenticationEntryPoint(accessDeniedHandler)
                // 自定义无权限访问
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                // 基于token，因此不须要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()

                .and()
                .authorizeRequests()
                // 跨域的调用
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //  获取白名单（不进行权限验证）
                .antMatchers(props.getExcludePath()).permitAll()
                //  固定的白名单
                .antMatchers(
                        "/actuator/**",
                        "/v2/api-docs",
                        "/doc.html",
                        "/configuration/ui",
                        "/swagger-resources",
                        "/configuration/security",
                        "/webjars/**",
                        "/swagger-resources/configuration/ui",
                        "/swagger-ui.html",
                        "/error",
                        "/error/**"
                ).permitAll()
                //  其他的请求全部要认证
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .cacheControl();
    }


}
