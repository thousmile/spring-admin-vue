package com.xaaef.robin.util.useragent.browser;

import com.xaaef.robin.util.useragent.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doujiajun 402550833@qq.com
 */
public enum EngineEnum {

    NETFRONT("netfront", "NetFront"),

    EDGE("edge", "Edge"),

    TRIDENT("trident/(([a-z0-9\\.\\-\\+]+))", "Trident"),

    TRIDENT1("trident", "Trident"),

    BLINK("blink", "Blink"),

    WEBKIT("[?:apple]webkit/((.*?)) ", "WebKit"),

    PRESTO("presto/((.*?)) ", "Presto"),

    PRESTO1("presto", "Presto"),

    GECKO("gecko/((\\d+))", "Gecko"),

    GECKO1("(?<!like )gecko", "Gecko"),

    KHTML("khtml", "KHTML"),

    NETSURF("netsurf", "NetSurf"),

    SERVO("servo", "Servo");

    private String regex;

    private String family;

    EngineEnum(final String regex, final String family) {
        this.regex = regex;
        this.family = family;
    }

    public String getRegex() {
        return regex;
    }

    public String getFamily() {
        return family;
    }

    public static Engine match(String agentString) {
        for (EngineEnum info : values()) {
            Pattern pattern = Pattern.compile(info.getRegex());
            Matcher matcher = pattern.matcher(agentString.toLowerCase());
            if (!matcher.find()) {
                continue;
            }
            String engineFamily = info.getFamily();
            int groupCount = matcher.groupCount();
            String version = Constants.DEFAULT_VALUE;
            if (groupCount > 1) {
                version = matcher.group(1);
            }
            return StringUtils.isBlank(engineFamily) ? null : new Engine(engineFamily, version);
        }
        return Engine.DEFAULT_ENGINE;
    }
}
