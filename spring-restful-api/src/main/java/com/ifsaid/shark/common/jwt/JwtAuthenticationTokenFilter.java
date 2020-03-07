package com.ifsaid.shark.common.jwt;

import com.ifsaid.shark.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * All rights Reserved, Designed By www.ifsaid.com
 * <p>
 * jwt核心拦截器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.ifsaid.com/ Inc. All rights reserved.
 */


@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 获取当前登录用户的信息
     * JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     *
     * @throws ServletException
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/19 0:11
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取 Request 中的请求头为 “ Authorization ” 的 token 值
        String completeToken = request.getHeader(this.jwtTokenUtils.getTokenHeader());
        // 验证 值是否以"Bearer "开头
        if (completeToken != null && completeToken.startsWith(this.jwtTokenUtils.getTokenHead())) {
            // 截取token中"Bearer "后面的值，
            final String tokenValue = jwtTokenUtils.interceptCompleteToken(completeToken);
            // 根据 token值，获取 用户的 username
            String username = jwtTokenUtils.getUsernameFromToken(tokenValue);
            log.debug("当前登录的用户是 : {} ", username);
            // 验证用户账号是否合法
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据 username 去 redis 中查询 user 数据，足够信任token的情况下，可以省略这一步
                JwtUser userDetails = null;
                try {
                    userDetails = sysUserService.validateUsername(username);
                } catch (AuthenticationException ex) {
                    log.debug(ex.getMessage());
                    SecurityContextHolder.clearContext();
                    this.authenticationEntryPoint.commence(request, response, ex);
                    return;
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将用户信息，设置到 SecurityContext 中，可以在任何地方 使用 下面语句获取 获取 当前用户登录信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


}
