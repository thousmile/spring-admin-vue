package com.xaaef.robin.util.useragent.os;

import com.xaaef.robin.util.useragent.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doujiajun 402550833@qq.com
 */
public enum PlatformEnum {

    ARM("arm", "ARM"),

    X64("[wow|x|win|amd64|x86_]64", "x64"),

    X86("i[0-9]86|i86pc", "x86");

    private String regex;

    private String family;

    public String getRegex() {
        return regex;
    }

    public String getFamily() {
        return family;
    }

    PlatformEnum(final String regex, final String family) {
        this.regex = regex;
        this.family = family;
    }

    public static String match(String agentString) {
        for (PlatformEnum info : values()) {
            Pattern pattern = Pattern.compile(info.getRegex());
            Matcher matcher = pattern.matcher(agentString.toLowerCase());
            if (!matcher.find()) {
                continue;
            }
            return info.getFamily();
        }
        return Constants.DEFAULT_VALUE;
    }
}
