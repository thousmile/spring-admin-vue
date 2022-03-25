package com.xaaef.robin.util.useragent.os;

import com.xaaef.robin.util.useragent.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author doujiajun 402550833@qq.com
 */
public class OsPattern {
    private final Pattern pattern;
    private final String vendor;
    private final String osName;
    private final boolean isMobile;
    private final String majorVersion;
    private final String minorVersion;
    private final boolean isTv;

    OsPattern(Pattern pattern, String vendor, String osName, String majorVersion, String minorVersion, boolean isMobile, boolean isTv) {
        this.pattern = pattern;
        this.vendor = vendor;
        this.osName = osName;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
        this.isMobile = isMobile;
        this.isTv = isTv;
    }

    public static OsPattern addFromMap(Map<String, String> patternMap) {
        String regex = patternMap.get(Constants.REGEX);
        if (StringUtils.isBlank(regex)) {
            throw new IllegalArgumentException("OS regex missing");
        }
        String osVendorString = patternMap.get(Constants.VENDOR);
        String osNameString = patternMap.get(Constants.OS);
        String major = patternMap.get(Constants.MAJOR);
        String minor = patternMap.get(Constants.MINOR);
        String isMobileExpr = patternMap.get(Constants.IS_MOBILE);
        String isTVExpr = patternMap.get(Constants.IS_TV);
        boolean isMobileString = false;
        if (StringUtils.isNotBlank(isMobileExpr) && "true".equals(isMobileExpr.toLowerCase())) {
            isMobileString = true;
        }
        boolean isTvString = false;
        if (StringUtils.isNotBlank(isTVExpr) && "true".equals(isTVExpr.toLowerCase())) {
            isTvString = true;
        }
        return new OsPattern(Pattern.compile(regex), osVendorString, osNameString, major, minor, isMobileString, isTvString);
    }

    public Os match(String agentString) {
        String osVendor, osFamily = null, major = null, minor = null;
        Matcher matcher = pattern.matcher(agentString);
        if (!matcher.find()) {
            return null;
        }
        //os family
        int groupCount = matcher.groupCount();
        if (StringUtils.isNotBlank(osName)) {
            osFamily = osName;
        } else if (groupCount > 0) {
            osFamily = matcher.group(1);
        }
        // os vendor
        if (StringUtils.isNotBlank(vendor)) {
            osVendor = vendor;
        } else {
            osVendor = osFamily;
        }

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
        //check family
        if (StringUtils.isBlank(osFamily)) {
            return null;
        }
        String platform = PlatformEnum.match(agentString);
        return new Os(osVendor, osFamily, major, minor, isMobile, isTv, platform);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String getVendor() {
        return vendor;
    }

    public String getOsName() {
        return osName;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public String getMajorVersion() {
        return majorVersion;
    }

    public String getMinorVersion() {
        return minorVersion;
    }

    public boolean isTv() {
        return isTv;
    }
}
