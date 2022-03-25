package com.xaaef.robin.jwt;

import com.xaaef.robin.enums.OAuth2Error;
import com.xaaef.robin.exception.JwtAuthException;
import com.xaaef.robin.service.JwtTokenService;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.util.JsonUtils;
import com.xaaef.robin.util.ServletUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * <p>
 * jwt核心拦截器
 * </p>
 *
 * @author Wang Chen Chen <932560435@qq.com>
 * @version 2.0
 * @date 2019/4/18 11:45
 */


@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtTokenService tokenService;

    private JwtTokenProperties props;

    // spring 路径匹配工具
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * @param url 需要验证的 url 地址
     * @author Wang Chen Chen
     * @date 2021/7/9 10:14
     */
    public boolean urlIgnore(String url) {
        for (String pattern : props.getExcludePath()) {
            if (PATH_MATCHER.match(pattern, url)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取当前登录用户的信息
     * JwtUserDetails userDetails = JwtSecurityUtils.getLoginUser()
     *
     * @throws ServletException
     * @throws IOException
     * @author Wang Chen Chen<932560435@qq.com>
     * @date 2019/12/19 0:11
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 此路径忽略
        if (urlIgnore(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        // 获取 Request 中的请求头为 “ Authorization ” 的 token 值
        String bearerToken = request.getHeader(props.getTokenHeader());
        if (StringUtils.hasText(bearerToken)) {
            try {
                // 根据 token 去 redis 中查询 user 数据，足够信任token的情况下，可以省略这一步
                var userDetails = tokenService.validate(bearerToken);
                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将用户信息，设置到 SecurityContext 中，可以在任何地方 使用 下面语句获取 获取 当前用户登录信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtAuthException ex) {
                SecurityContextHolder.clearContext();
                ServletUtils.renderError(response, ex);
                return;
            } catch (Exception ex) {
                SecurityContextHolder.clearContext();
                ServletUtils.renderError(response, OAuth2Error.OAUTH2_EXCEPTION.getStatus(), ex.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }


}
