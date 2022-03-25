package com.xaaef.robin.util.useragent.os;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author doujiajun 402550833@qq.com
 */
public class OsParser {
    private List<OsPattern> patterns;

    public OsParser(List<OsPattern> patterns) {
        this.patterns = patterns;
    }

    public static OsParser addList(List<Map<String, String>> list) {
        return new OsParser(
                list.stream().map(config -> {
                    return OsPattern.addFromMap(config);
                }).collect(Collectors.toList())
        );
    }

    public Os parse(String userAgentString) {
        if (StringUtils.isBlank(userAgentString)) {
            return Os.DEFAULT_OS;
        }

        Os os;
        for (OsPattern p : patterns) {
            if ((os = p.match(userAgentString)) != null) {
                return os;
            }
        }
        return Os.DEFAULT_OS;
    }
}
