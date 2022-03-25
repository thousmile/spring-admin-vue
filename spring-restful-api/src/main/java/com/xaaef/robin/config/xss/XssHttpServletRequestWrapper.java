package com.xaaef.robin.config.xss;

import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author WangChenChen
 * @version 1.0
 * @date 2022/3/25 9:28
 */

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return cleanXSS(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        return cleanXSS(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = cleanXSS(values[i]);
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }

    /**
     * 主要是针对HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE 获取pathvalue的时候把原来的pathvalue经过xss过滤掉
     */
    @Override
    public Object getAttribute(String name) {
        // 获取pathvalue的值
        if (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE.equals(name)) {
            var uriTemplateVars = (Map) super.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (Objects.isNull(uriTemplateVars)) {
                return uriTemplateVars;
            }
            var newMap = new LinkedHashMap<>();
            uriTemplateVars.forEach((key, value) -> {
                if (value instanceof String) {
                    newMap.put(key, cleanXSS((String) value));
                } else {
                    newMap.put(key, value);

                }
            });
            return newMap;
        } else {
            return super.getAttribute(name);
        }
    }


    public static String cleanXSS(String value) {
        if (Objects.isNull(value)) {
            return value;
        }
        //You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        return value;
    }


}
