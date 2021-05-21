package com.xaaef.shark.common.jwt;

import com.xaaef.shark.exception.JwtAuthenticationException;
import com.xaaef.shark.service.UserLoginService;
import com.xaaef.shark.util.JsonResult;
import com.xaaef.shark.util.JsonUtils;
import com.xaaef.shark.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * All rights Reserved, Designed By www.xaaef.com
 * <p>
 * jwt核心拦截器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 * @copyright 2019 http://www.xaaef.com/ Inc. All rights reserved.
 */


@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserLoginService userLoginService;

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
        String bearerToken = request.getHeader(jwtTokenUtils.getTokenHeader());
        if (StringUtils.hasText(bearerToken)) {
            String tokenValue = jwtTokenUtils.getTokenValue(bearerToken);
            if (StringUtils.hasText(tokenValue)) {
                // 根据 username 去 redis 中查询 user 数据，足够信任token的情况下，可以省略这一步
                JwtLoginUser userDetails = null;
                try {
                    String loginId = jwtTokenUtils.getIdFromToken(tokenValue);
                    userDetails = userLoginService.validateUser(loginId);
                } catch (AuthenticationException ex) {
                    SecurityContextHolder.clearContext();
                    ServletUtils.renderString(response, JsonUtils.objectToJson(
                            JsonResult.result(HttpStatus.FORBIDDEN.value(), ex.getMessage())
                    ));
                    return;
                } catch (Exception ex) {
                    SecurityContextHolder.clearContext();
                    ServletUtils.renderString(response, JsonUtils.objectToJson(
                            JsonResult.result(HttpStatus.UNAUTHORIZED.value(), ex.getMessage())
                    ));
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
