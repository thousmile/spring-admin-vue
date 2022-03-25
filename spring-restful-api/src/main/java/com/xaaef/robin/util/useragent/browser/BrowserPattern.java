package com.xaaef.robin.util.useragent.browser;

import com.xaaef.robin.util.useragent.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doujiajun 402550833@qq.com
 */
public class BrowserPattern {
    private final Pattern pattern;
    private final String vendor;
    private final String family;
    private final String majorVersion;
    private final String minorVersion;

    BrowserPattern(Pattern pattern, String vendor, String family, String majorVersion, String minorVersion) {
        this.pattern = pattern;
        this.vendor = vendor;
        this.family = family;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    public static BrowserPattern addFromMap(Map<String, String> patternMap) {
        String regex = patternMap.get(Constants.REGEX);
        if (StringUtils.isBlank(regex)) {
            throw new IllegalArgumentException("browser regex missing");
        }

        String browserVendor = patternMap.get(Constants.VENDOR);
        String browserFamily = patternMap.get(Constants.FAMILY);
        String major = patternMap.get(Constants.MAJOR);
        String minor = patternMap.get(Constants.MINOR);

        return new BrowserPattern(Pattern.compile(regex), browserVendor, browserFamily, major, minor);
    }

    public Browser match(String agentString) {
        String browserFamily = null, major = null, minor = null;
        Matcher matcher = pattern.matcher(agentString.toLowerCase());
        if (!matcher.find()) {
            return null;
        }

        int groupCount = matcher.groupCount();
        if (family != null) {
            browserFamily = family;
        } else if (groupCount > 0) {
            /* this is matcher.group(1) because of matcher.group(0) is the full expression */
            browserFamily = matcher.group(1);
        }

        String browserVendor = (StringUtils.isBlank(vendor)) ? browserFamily : vendor;

        if (StringUtils.isNotBlank(majorVersion)) {
            major = majorVersion;
        } else if (groupCount > 1) {
            major = matcher.group(2);
        }

        if (StringUtils.isNotBlank(minorVersion)) {
            minor = minorVersion;
        } else if (groupCount > 2) {
            minor = matcher.group(3);
        }
        //parse engine
        Engine engine = EngineEnum.match(agentString);
        return StringUtils.isBlank(browserFamily) ? null : new Browser(browserVendor, browserFamily, major, minor, engine);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getVendor() {
        return vendor;
    }

    public String getFamily() {
        return family;
    }

    public String getMajorVersion() {
        return majorVersion;
    }

    public String getMinorVersion() {
        return minorVersion;
    }
}
