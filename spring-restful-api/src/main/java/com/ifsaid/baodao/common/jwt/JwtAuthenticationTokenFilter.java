package com.ifsaid.baodao.common.jwt;

import com.ifsaid.baodao.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 14:29
 * @describe： jwt核心拦截器
 * @version: 1.0
 */

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Lazy
    private IRedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 获取 Request 中的请求头为 “ Authorization ” 的 token 值
        String authHeader = request.getHeader(this.jwtTokenUtil.getTokenHeader());
        // 验证 值是否以"Bearer "开头
        if (authHeader != null && authHeader.startsWith(this.jwtTokenUtil.getTokenHead())) {
            // 截取token中"Bearer "后面的值，
            final String authToken = authHeader.substring(this.jwtTokenUtil.getTokenHead().length());
            // 获取用户账号
            String account = jwtTokenUtil.getUsernameFromToken(authToken);
            log.info("JwtAuthenticationTokenFilter[doFilterInternal] checking authentication {} ", account);
            // 验证用户账号是否合法
            if (account != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 根据account去数据库中查询user数据，足够信任token的情况下，可以省略这一步
                JwtUser userDetails = (JwtUser) redisService.getMapField(jwtTokenUtil.getTokenHeader(), account);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("authenticated user {} setting security context", account);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
