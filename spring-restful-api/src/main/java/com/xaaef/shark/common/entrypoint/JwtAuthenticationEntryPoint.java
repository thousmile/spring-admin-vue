package com.xaaef.shark.common.entrypoint;

import com.xaaef.shark.util.JsonResult;
import com.xaaef.shark.util.JsonUtils;
import com.xaaef.shark.util.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
        JsonResult<String> result = JsonResult.result(HttpStatus.UNAUTHORIZED.value(), msg);
        ServletUtils.renderString(response, JsonUtils.objectToJson(result));
    }

}