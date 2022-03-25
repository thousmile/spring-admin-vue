package com.xaaef.robin.util;

import com.xaaef.robin.util.useragent.UserAgent;
import com.xaaef.robin.util.useragent.UserAgentParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 * 请求工具类
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0
 * @date 2021/7/5 10:50
 */

@Slf4j
public class RequestUtils {

    private static UserAgentParser parser;

    static {
        try {
            parser = new UserAgentParser();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("UserAgentParser :  {}", e.getMessage());
        }
    }


    /**
     * 获取客户端信息
     *
     * @return
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        return parser.parse(ua);
    }


    /**
     * 获取客户端信息
     */
    public static UserAgent getUserAgent(String ua) {
        return parser.parse(ua);
    }


    /**
     * 获取 请求全路径
     */
    public static String getFullPath(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            url.append("?").append(request.getQueryString());
        }
        return url.toString();
    }


}
