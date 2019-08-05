package com.ifsaid.baodao.common.entrypoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsaid.baodao.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/11/16 14:54
 * @describe：
 * @version: 1.0
 */

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Result result = null;
        /**身份认证未通过*/
        if (authException instanceof BadCredentialsException) {
            result = Result.error401("用户名或密码错误，请重新输入！", authException.getMessage());
        } else {
            result = Result.error401("无效的token！", authException.getMessage());
        }
        response.getWriter().write(mapper.writeValueAsString(result));
    }
}
