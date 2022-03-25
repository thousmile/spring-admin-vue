package com.xaaef.robin.util;

import com.xaaef.robin.exception.JwtAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 客户端工具类
 *
 * @author wangwei
 */
public class ServletUtils {
    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return null
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param ex       待渲染的字符串
     * @return null
     */
    public static void renderError(HttpServletResponse response, JsonResult result) {
        renderString(response, JsonUtils.toJson(result));
    }


    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param ex       待渲染的字符串
     * @return null
     */
    public static void renderError(HttpServletResponse response, JwtAuthException ex) {
        renderError(response, ex.getStatus(), ex.getMessage());
    }


    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param ex       待渲染的字符串
     * @return null
     */
    public static void renderError(HttpServletResponse response, int status, String message) {
        String json = JsonUtils.toJson(JsonResult.error(status, message));
        renderString(response, json);
    }

}
