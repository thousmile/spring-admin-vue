package com.xaaef.robin.jwt;

import com.xaaef.robin.enums.OAuth2Error;
import com.xaaef.robin.util.JsonResult;
import com.xaaef.robin.util.JsonUtils;
import com.xaaef.robin.util.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    /**
     * 权限不足，
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        String msg = String.format("请求访问：%s，权限不足，请联系管理员", request.getRequestURI());
        ServletUtils.renderError(response, OAuth2Error.ACCESS_DENIED.getStatus(), msg);
    }


    /**
     * 未认证
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderError(response, OAuth2Error.OAUTH2_EXCEPTION.getStatus(), msg);
    }

}
