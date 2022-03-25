package com.xaaef.robin.util.useragent.os;


import com.xaaef.robin.util.useragent.Constants;

/**
 * @author doujiajun 402550833@qq.com
 */
public class Os {
    public static final Os DEFAULT_OS = new Os(Constants.DEFAULT_VALUE, Constants.DEFAULT_VALUE, "0", "0", false, false, Constants.DEFAULT_VALUE);

    private final String vendor;
    private final String family;
    private final String major;
    private final String minor;
    private final boolean isMobile;
    private final boolean isTv;
    private final String platform;

    Os(String vendor, String family, String major, String minor, boolean isMobile, boolean isTv, String platform) {
        this.vendor = vendor;
        this.family = family;
        this.major = major;
        this.minor = minor;
        this.isMobile = isMobile;
        this.isTv = isTv;
        this.platform = platform;
    }

    public String getVendor() {
        return vendor;
    }

    public String getFamily() {
        return family;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public boolean isTv() {
        return isTv;
    }

    public String getPlatform() {
        return platform;
    }
}
