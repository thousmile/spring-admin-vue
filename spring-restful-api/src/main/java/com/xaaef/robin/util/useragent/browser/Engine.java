package com.xaaef.robin.util.useragent.browser;


import com.xaaef.robin.util.useragent.Constants;

/**
 * @author doujiajun 402550833@qq.com
 */
public class Engine {
    public static final Engine DEFAULT_ENGINE = new Engine(Constants.DEFAULT_VALUE, Constants.DEFAULT_VALUE);
    private final String family;
    private final String version;

    Engine(String family, String version) {
        this.family = family;
        this.version = version;
    }

    public String getName() {
        return family;
    }

    public String getVersion() {
        return version;
    }
}
